
package Controller.restapi.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        extends Controller.restapi.support.ResponseWrapper
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

    }

    public class GetProductsResponse
        extends Controller.restapi.support.ResponseWrapper
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

    }

    public class PostProductsResponse
        extends Controller.restapi.support.ResponseWrapper
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

    }

    public class PutProductsByBarcodeResponse
        extends Controller.restapi.support.ResponseWrapper
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

    }

}
