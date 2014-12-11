
package Controller.restapi.resource;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;


/**
 * Colection containing list of food groups
 * 
 */
@Path("foodgroups")
public interface Foodgroups {


    /**
     * Creates a new food group
     * 
     * @param name
     *     Name of a new food group e.g. Fruit
     */
    @POST
    @Produces({
        "application/json"
    })
    Foodgroups.PostFoodgroupsResponse postFoodgroups(
        @QueryParam("name")
        String name)
        throws Exception
    ;

    /**
     * Retrieves products assigned to a food group
     * 
     * @param name
     *     
     */
    @GET
    @Path("{name}/products")
    @Produces({
        "application/json"
    })
    Foodgroups.GetFoodgroupsByNameProductsResponse getFoodgroupsByNameProducts(
        @PathParam("name")
        String name)
        throws Exception
    ;

    public class GetFoodgroupsByNameProductsResponse
        extends Controller.restapi.support.ResponseWrapper
    {


        private GetFoodgroupsByNameProductsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data": [
         *     {
         *     "barcode": 20000233,
         *     "food_group_id": 6,
         *     "product_name": "Grana Padano",
         *     "price": 3.16,
         *     "quantity_number": 100,
         *     "unit": "gr",
         *     "serving_size": null,
         *     "calories": 567 , 
         *     "proteins": 4.5,
         *     "carbohydrates": 23,
         *     "sugar": 2.3,
         *     "fat": null,
         *     "saturated_fat": null,
         *     "fiber": 0.9,
         *     "cholesterol": 2.4,
         *     "sodium": 0.04,
         *     "calcium": 0.56,
         *     "iron": null,
         *     "vitamin_a": null,
         *     "vitamin_c": null
         *     },
         *     {
         *     "barcode": 20000456,
         *     "food_group_id": 3,
         *     "product_name": "Soja Nature",
         *     "price": 34.12,
         *     "quantity_number": 500,
         *     "unit": "gr",
         *     "serving_size": null,
         *     "calories": 867 , 
         *     "proteins": 56,
         *     "carbohydrates": 123,
         *     "sugar": 4.6,
         *     "fat": 12.34,
         *     "saturated_fat": 9.8,
         *     "fiber": 0.5,
         *     "cholesterol": 0,
         *     "sodium": 0.23,
         *     "calcium": 5.78,
         *     "iron": null,
         *     "vitamin_a": 0,
         *     "vitamin_c": 0
         *     }
         *   ],
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data": [
         *         {
         *         "barcode": 20000233,
         *         "food_group_id": 6,
         *         "product_name": "Grana Padano",
         *         "price": 3.16,
         *         "quantity_number": 100,
         *         "unit": "gr",
         *         "serving_size": null,
         *         "calories": 567 , 
         *         "proteins": 4.5,
         *         "carbohydrates": 23,
         *         "sugar": 2.3,
         *         "fat": null,
         *         "saturated_fat": null,
         *         "fiber": 0.9,
         *         "cholesterol": 2.4,
         *         "sodium": 0.04,
         *         "calcium": 0.56,
         *         "iron": null,
         *         "vitamin_a": null,
         *         "vitamin_c": null
         *         },
         *         {
         *         "barcode": 20000456,
         *         "food_group_id": 3,
         *         "product_name": "Soja Nature",
         *         "price": 34.12,
         *         "quantity_number": 500,
         *         "unit": "gr",
         *         "serving_size": null,
         *         "calories": 867 , 
         *         "proteins": 56,
         *         "carbohydrates": 123,
         *         "sugar": 4.6,
         *         "fat": 12.34,
         *         "saturated_fat": 9.8,
         *         "fiber": 0.5,
         *         "cholesterol": 0,
         *         "sodium": 0.23,
         *         "calcium": 5.78,
         *         "iron": null,
         *         "vitamin_a": 0,
         *         "vitamin_c": 0
         *         }
         *       ],
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Foodgroups.GetFoodgroupsByNameProductsResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Foodgroups.GetFoodgroupsByNameProductsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         */
        public static Foodgroups.GetFoodgroupsByNameProductsResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Foodgroups.GetFoodgroupsByNameProductsResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

    public class PostFoodgroupsResponse
        extends Controller.restapi.support.ResponseWrapper
    {


        private PostFoodgroupsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         * "success": true,
         * "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": true,
         *     "status": 200
         *     }
         *     
         */
        public static Foodgroups.PostFoodgroupsResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Foodgroups.PostFoodgroupsResponse(responseBuilder.build());
        }

        /**
         *  e.g. {
         * "success": false,
         * "status": 400
         * }
         * 
         * 
         * @param entity
         *     {
         *     "success": false,
         *     "status": 400
         *     }
         *     
         */
        public static Foodgroups.PostFoodgroupsResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Foodgroups.PostFoodgroupsResponse(responseBuilder.build());
        }

		@Override
		public boolean bufferEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Set<String> getAllowedMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, NewCookie> getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getDate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public EntityTag getEntityTag() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHeaderString(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Locale getLanguage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Date getLastModified() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Link getLink(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Builder getLinkBuilder(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Link> getLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public URI getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MediaType getMediaType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StatusType getStatusInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MultivaluedMap<String, String> getStringHeaders() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasEntity() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasLink(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T readEntity(Class<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(Class<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T readEntity(GenericType<T> arg0, Annotation[] arg1) {
			// TODO Auto-generated method stub
			return null;
		}

    }

}
