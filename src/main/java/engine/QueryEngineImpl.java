package engine;

import li3.*;
import java.lang.*;
import java.util.*;

public class QueryEngineImpl implements Interface {

    private Structure struct;

    public QueryEngineImpl(){}

        /**
        * Inicia a estrutura
        */
        public void init() {
            this.struct = new Structure();
        }

        /**
        * Carrega a estrutura com os dados
        * @param nsnaps número de snapshots a processar
        * @param snaps_paths path dos snapshots a processar
        */
        public void load(int nsnaps, ArrayList<String> snaps_paths) {
            new Parser().run(this.struct, nsnaps, snaps_paths);
        }

        /**
        * Devolve o número total de artigos nos snapshots
        * @return  número total de artigos nos snapshots
        */
        public long all_articles() {
            return this.struct.getAllArticles();
        }

        /**
        * Devolve o número de artigos únicos nos snapshots (caso existam)
        * @return  número de artigos únicos nos snapshots
        */
        public long unique_articles() {
            try{
                return this.struct.getArticles().size();
            }catch(NoArticlesException e){
                System.out.println("Sem artigos");
                return 0;
            }
        }

        /**
        * Devolve o número de revisões únicas nos snapshots (caso existam)
        * @return  número de revisões únicas nos snapshots
        */
        public long all_revisions() {
            try{
                return this.struct.getArticles().values().stream().mapToLong(Article :: getNRevisions).sum();
            }catch(NoArticlesException e){
                System.out.println("Sem artigos");
                return 0;
            }
        }

        /**
        * Devolve o 10 maiores contribuidores (caso existam)
        * @return ArrayList com os 10 maiores contribuidores
        */
        public ArrayList<Long> top_10_contributors() {
            ArrayList<Long> top_ten_ids = new ArrayList<Long>(10);
            try{
                this.struct.getContributors().values().stream()
                .sorted(new ContributorComparator())
                .limit(10)
                .forEach(c -> top_ten_ids.add(c.getId()));
            }catch(NoContributorsException e){System.out.println("Sem contribuidores");}
            return top_ten_ids;
        }

        /**
        * Devolve o nome do contribuidor do ID passado como argumento (caso exista)
        * @param contributor_id ID do contribuidor a encontrar o nome
        * @return ArrayList com os 10 maiores contribuidores
        */
        public String contributor_name(long contributor_id) {
            try{
                return this.struct.getContributors().get(contributor_id).getName();
            }catch(NoContributorsException e){System.out.println("Sem contribuidores");}
            return null;
        }

        /**
        * Devolve os 20 maiores artigos
        * @return ArrayList com os 20 maiores artigos
        */
        public ArrayList<Long> top_20_largest_articles() {
            ArrayList<Long> top_twenty_ids = new ArrayList<Long>(20);
            try{
                this.struct.getArticles().values().stream()
                .sorted(new ArticleCharComparator())
                .limit(20)
                .forEach(a -> top_twenty_ids.add((a.getId())));
            }catch(NoArticlesException e){System.out.println("Sem artigos");}
            return top_twenty_ids;
        }

        /**
        * Devolve o titulo de um artigo dado o seu ID
        * @param article_id ID do artigo
        * @return Titulo do artigo
        */
        public String article_title(long article_id) {
            try{
                Article art = this.struct.getArticles().get(article_id);
                return art.getRecentRevision().getTitle();
            }catch(NoArticlesException e){System.out.println("Sem artigos");}
            return null;
        }

        /**
        * Devolve os N artigos com mais palavras
        * @param n Número de artigos que se pretende saber
        * @return ArrayList com os N artigos com mais palavras
        */
        public ArrayList<Long> top_N_articles_with_more_words(int n) {
            ArrayList<Long> top_n_ids = new ArrayList<Long>(n);
            try{
                this.struct.getArticles().values().stream()
                                                  .sorted(new ArticleWordComparator())
                                                  .limit(n)
                                                  .forEach(a -> top_n_ids.add((a.getId())));

            }catch(NoArticlesException e){System.out.println("Sem artigos");}
            return top_n_ids;
        }

        /**
        * Devolve os titulos com um deteminado prefixo
        * @param prefix Prefixo a verificar
        * @return ArrayList com os titulos que contém um prefixo
        */
        public ArrayList<String> titles_with_prefix(String prefix) {
            ArrayList<String> res = new ArrayList<String>();
            try{
                this.struct.getArticles().values().stream()
                                                  .map(Article::getRecentRevision)
                                                  .filter(r -> r.getTitle().startsWith(prefix))
                                                  .map(Revision::getTitle)
                                                  .sorted(new TitleComparator())
                                                  .forEach(title -> res.add(title));

            }catch(NoArticlesException e){System.out.println("Sem artigos");}
            return res;
        }

        /**
        * Devolve o timestamp de um artigo e de uma revisão dados com argumento
        * @param article_id ID do artigo
        * @param revision_id ID da revisão
        * @return ArrayList com os 10 maiores contribuidores
        */
        public String article_timestamp(long article_id, long revision_id) {
            try{
                if(this.struct.getArticles().containsKey(article_id) && this.struct.getArticles().get(article_id).getRevision(revision_id)!=null)
                return this.struct.getArticles().get(article_id).getRevision(revision_id).getDate();
            }catch(NoArticlesException e){System.out.println("Sem artigos");}
            return null;
        }

        /**
        * Liberta a estrutura
        */
        public void clean() {
            try{
                try{
                    this.struct.getArticles().values().stream()
                                                      .map(Article::safeGetRevSet)
                                                      .forEach(set -> set.clear());

                }catch(RuntimeException e){System.out.println("Sem Revisões");}

                    this.struct.getArticles().clear();
            }catch(NoArticlesException e){System.out.println("Sem artigos");}

            try{
                this.struct.getContributors().clear();
            }catch(NoContributorsException e){System.out.println("Sem contribuidores");}

        }

    }
