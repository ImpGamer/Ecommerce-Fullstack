import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Product } from "../class/product";
import { HeaderService } from "./header.service";
//Clase de servicio donde consumiremos los controladores de servicio del backend
@Injectable({
    providedIn:'root'
})

export class ProductService {
    private API_URL:string = "http://localhost:8085/admin/products"

    constructor(private HttpClient:HttpClient,private headersService:HeaderService){}

    //Nos adherimos a la visualizacion de un endpoint para obtener los datos asincronicamente
    getProducts():Observable<Product[]> {
        return this.HttpClient.get<Product[]>(this.API_URL,{headers:this.headersService.headers})
    }
    createProduct(producto:Product,image:File):Observable<any>{
        const formData = new FormData();
        formData.append('product', new Blob([JSON.stringify(producto)], { type: 'application/json' }));
        formData.append('image', image);
        //El metodo post tendra dos parametros, la endpoint del backend y el ResponseBody que mandara
        return this.HttpClient.post<Product>(`${this.API_URL}/add`,formData,{headers:this.headersService.headers})
    }
    deleteProduct(productId:number):Observable<any> {
        return this.HttpClient.delete(this.API_URL,{
            params: {id:productId}, //Manera de enviar parametros en la endpoints
            headers:this.headersService.headers
        })
    }
    getProductById(productId:number):Observable<Product> {
        return this.HttpClient.get<Product>(`${this.API_URL}/find`,{
            params:{id:productId},
            headers:this.headersService.headers
        })
    }
    updateProduct(id:number,product:Product,image:File):Observable<any> {
        const formData = new FormData();
        formData.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }));
        formData.append('image', image);

        return this.HttpClient.put<Product>(`${this.API_URL}/update/${id}`,formData,{headers:this.headersService.headers})
    }
}
