
package Controller.org.raml.jaxrs.test.resource;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
 * Collection containing list of all products sold in a supermarket
 * 
 */
@Path("products")
public interface Products {


    /**
     * Retrieves barcodes of all products
     * 
     */
    @GET
    @Produces({
        "application/json"
    })
    Products.GetProductsResponse getProducts()
        throws Exception
    ;

    /**
     * Creates a new product
     * 
     * @param price
     *     Price of a product e.g. 5.60
     * @param name
     *     Name of a product e.g. Soja Nature
     * @param barcode
     *     Product barcode e.g. 20000233
     * @param foodGroupId
     *     Id of a food group, which a product is assigned to e.g. 6
     */
    @POST
    @Produces({
        "application/json"
    })
    Products.PostProductsResponse postProducts(
        @QueryParam("barcode")
        BigDecimal barcode,
        @QueryParam("name")
        String name,
        @QueryParam("foodGroupId")
        BigDecimal foodGroupId,
        @QueryParam("price")
        BigDecimal price)
        throws Exception
    ;

    /**
     * Retrieves details of a product
     * 
     * @param barcode
     *     
     */
    @GET
    @Path("{barcode}")
    @Produces({
        "application/json"
    })
    Products.GetProductsByBarcodeResponse getProductsByBarcode(
        @PathParam("barcode")
        String barcode)
        throws Exception
    ;

    /**
     * Updates a product
     * 
     * @param price
     *     Price of a product e.g. 5.60
     * @param name
     *     Name of a product e.g. Soja Nature
     * @param barcode
     *     
     * @param foodGroupId
     *     Id of a food group, which a product is assigned to e.g. 6
     */
    @PUT
    @Path("{barcode}")
    @Produces({
        "application/json"
    })
    Products.PutProductsByBarcodeResponse putProductsByBarcode(
        @PathParam("barcode")
        String barcode,
        @QueryParam("name")
        String name,
        @QueryParam("foodGroupId")
        BigDecimal foodGroupId,
        @QueryParam("price")
        BigDecimal price)
        throws Exception
    ;

    public class GetProductsByBarcodeResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetProductsByBarcodeResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data":
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
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data":
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
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Products.GetProductsByBarcodeResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.GetProductsByBarcodeResponse(responseBuilder.build());
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
        public static Products.GetProductsByBarcodeResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.GetProductsByBarcodeResponse(responseBuilder.build());
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

    public class GetProductsResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private GetProductsResponse(Response delegate) {
            super(delegate);
        }

        /**
         *  e.g. {
         *   "data": {
         *     "_links": {
         *       "self": {
         *         "href":"/products"
         *       },
         *       "curies": [
         *         {
         *           "name": "np",
         *           "href": "http://54.149.71.241/api/products/{rel}",
         *           "templated": true
         *         }                    
         *       ],
         *       "np:product": [
         *         {
         *           "href": "/products/20000233",
         *           "title": "20000233"
         *         },
         *         {
         *           "href": "/products/20006006",
         *           "title": "20006006"
         *         }
         *       ]
         *     }
         *   },
         *   "success": true,
         *   "status": 200
         * }
         * 
         * 
         * @param entity
         *     {
         *       "data": {
         *         "_links": {
         *           "self": {
         *             "href":"/products"
         *           },
         *           "curies": [
         *             {
         *               "name": "np",
         *               "href": "http://54.149.71.241/api/products/{rel}",
         *               "templated": true
         *             }                    
         *           ],
         *           "np:product": [
         *             {
         *               "href": "/products/20000233",
         *               "title": "20000233"
         *             },
         *             {
         *               "href": "/products/20006006",
         *               "title": "20006006"
         *             }
         *           ]
         *         }
         *       },
         *       "success": true,
         *       "status": 200
         *     }
         *     
         */
        public static Products.GetProductsResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.GetProductsResponse(responseBuilder.build());
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

    public class PostProductsResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private PostProductsResponse(Response delegate) {
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
        public static Products.PostProductsResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.PostProductsResponse(responseBuilder.build());
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
        public static Products.PostProductsResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.PostProductsResponse(responseBuilder.build());
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

    public class PutProductsByBarcodeResponse
        extends Controller.org.raml.jaxrs.test.support.ResponseWrapper
    {


        private PutProductsByBarcodeResponse(Response delegate) {
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
        public static Products.PutProductsByBarcodeResponse jsonOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.PutProductsByBarcodeResponse(responseBuilder.build());
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
        public static Products.PutProductsByBarcodeResponse jsonBadRequest(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(400).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new Products.PutProductsByBarcodeResponse(responseBuilder.build());
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
