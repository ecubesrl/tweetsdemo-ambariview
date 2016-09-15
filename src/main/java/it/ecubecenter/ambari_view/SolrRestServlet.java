package it.ecubecenter.ambari_view;

import it.ecubecenter.solr.client.SolrConnection;
import org.apache.ambari.view.ViewContext;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocument;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by gaido on 02/09/2016.
 */
public class SolrRestServlet {

    private static String ZOOKEPER_PROPERTY="zookeper.solr.url";

    @Inject
    ViewContext context;


    @GET
    @Path("/users/topTenActive")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenUsers(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFacetField("screenName_s");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }

    @GET
    @Path("/users/topTenActiveLastHour")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenUsersLastHour(@Context HttpHeaders headers, @Context UriInfo ui) {
        //TODO: test with data...
        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFilterQuery("tw_created_at_dt:[NOW/SECOND-1HOUR TO NOW/SECOND]");
        query.addFacetField("screenName_s");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/hashtags/topTen")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenHashtag(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFacetField("hashtag");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/hashtags/topTenLastHour")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenHashtagLastHour(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFacetField("hashtag");
        query.addFilterQuery("tw_created_at_dt:[NOW/SECOND-1HOUR TO NOW/SECOND]");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }

    @GET
    @Path("/platforms/topTen")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenPltforms(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFacetField("piattaforma");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }

    @GET
    @Path("/clients/topTen")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenClients(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(false);
        query.addFacetField("client");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/countries/topTen")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenCountries(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(true);
        query.addFacetField("nationality");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            long totalCount = res.getResults().getNumFound() ;
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    if(cnt.getName() == null){
                        totalCount -= cnt.getCount();
                    }else {
                        jsonResult.put(cnt.getName(), cnt.getCount());
                    }
                }
            }

            jsonResult.put("Altro", totalCount);
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }

    @GET
    @Path("/countries/topTenLastHour")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenCountriesLastHour(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(true);
        query.addFacetField("nationality");
        query.addFilterQuery("tw_created_at_dt:[NOW/SECOND-1HOUR TO NOW/SECOND]");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            long totalCount = res.getResults().getNumFound() ;
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    if(cnt.getName() == null){
                        totalCount -= cnt.getCount();
                    }else {
                        jsonResult.put(cnt.getName(), cnt.getCount());
                    }
                }
            }

            jsonResult.put("Altro", totalCount);
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/languages/topTen")
    @Produces({"text/plain", "application/json"})
    public Response getTopTenLanguages(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetLimit(10);
        query.setFacetMissing(true);
        query.addFacetField("language");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            long totalCount = res.getResults().getNumFound() ;
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    if(cnt.getName() == null){
                        totalCount -= cnt.getCount();
                    }else {
                        jsonResult.put(cnt.getName(), cnt.getCount());
                    }
                }
            }

            jsonResult.put("Altro", totalCount);
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }



    @GET
    @Path("/sentiment/categories")
    @Produces({"text/plain", "application/json"})
    public Response getSentimetCategories(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.addFacetField("Sentiment_result_s");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(FacetField field : res.getFacetFields()){
                for(FacetField.Count cnt:field.getValues()){
                    jsonResult.put(cnt.getName(), cnt.getCount());
                }
            }

        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }



    @GET
    @Path("/tweets/lastTen")
    @Produces({"text/plain", "application/json"})
    public Response getLastTenTweets(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("id:*");
        query.setFields("text_t");
        query.addSort("tw_created_at_dt", SolrQuery.ORDER.desc);
        query.addFilterQuery("-retweetedID_l:*");
        query.setRows(10);

        JSONArray jsonResult = new JSONArray();
        try {

            QueryResponse res = solr.querySolr(query);
            for(SolrDocument doc : res.getResults()){
                jsonResult.put(doc.getFieldValues("text_t").iterator().next());
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/retweets/lastTen")
    @Produces({"text/plain", "application/json"})
    public Response getLastTenRetweets(@Context HttpHeaders headers, @Context UriInfo ui) {

        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("retweetedID_l:*");
        query.setFields("text_t");
        query.addSort("tw_created_at_dt", SolrQuery.ORDER.desc);
        query.setRows(10);

        JSONArray jsonResult = new JSONArray();
        try {

            QueryResponse res = solr.querySolr(query);
            for(SolrDocument doc : res.getResults()){
                jsonResult.put(doc.getFieldValues("text_t").iterator().next());
            }
        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


    @GET
    @Path("/monthsCount/lastYear")
    @Produces({"text/plain", "application/json"})
    public Response getMonthsCount(@Context HttpHeaders headers, @Context UriInfo ui) {
        //TODO : non funziona
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Date endDate = cal.getTime();
        endDate = new Date(endDate.getTime()+1000*60*60*24);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1, 0, 0, 0 );
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, -12);

        Date startDate = cal.getTime();


        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacet(true);
        query.setFacetMissing(false);
        query.addDateRangeFacet("tw_created_at_dt",startDate,endDate, "+1MONTH");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(RangeFacet<Date, String> field : res.getFacetRanges()){
                for(RangeFacet.Count cnt:field.getCounts()){
                    jsonResult.put(cnt.getValue(), cnt.getCount());
                }
            }

        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }

    @GET
    @Path("/hashtags/{hashtag}/countPerDay")
    @Produces({"text/plain", "application/json"})
    public Response getHashtagDayCount(@PathParam("hashtag") String hashtag, @Context HttpHeaders headers, @Context UriInfo ui) {
        //TODO: si spacca
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Date endDate = cal.getTime();
        endDate = new Date(endDate.getTime()+1000*60*60*24);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0 );
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, -1);
        Date startDate = cal.getTime();


        SolrConnection solr = SolrConnection.getInstance(context.getProperties().get(ZOOKEPER_PROPERTY));
        SolrQuery query = new SolrQuery();
        query.setQuery("hashtag:"+hashtag);
        query.setFacet(true);
        query.setFacetMissing(false);
        query.addDateRangeFacet("tw_created_at_dt",startDate,endDate, "+1DAY");
        query.setRows(0);

        JSONObject jsonResult = new JSONObject();
        try {

            QueryResponse res = solr.querySolr(query);
            for(RangeFacet<Date, String> field : res.getFacetRanges()){
                for(RangeFacet.Count cnt:field.getCounts()){
                    jsonResult.put(cnt.getValue(), cnt.getCount());
                }
            }

        } catch (Exception e) {

            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            return Response.serverError().build();
        }


        return Response.ok(jsonResult.toString()).build();
    }


}
