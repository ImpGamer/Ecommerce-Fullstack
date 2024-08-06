import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../class/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http:HttpClient) { }
  private API_URL:string = "http://localhost:8085/home"

  getProducts():Observable<Product[]> {
    return this.http.get<Product[]>(this.API_URL)
  }
  getProductById(productId:number):Observable<Product> {
    return this.http.get<Product>(`${this.API_URL}/find`,{
        params:{id:productId}
    })
}
}
