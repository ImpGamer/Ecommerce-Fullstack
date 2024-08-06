import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order, OrderStatus } from '../class/order';
import { Observable } from 'rxjs';
import { state } from '@angular/animations';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private API_URL:string = "http://localhost:8085/orders"

  constructor(private http:HttpClient,private headerService:HeaderService) { }

  createOrder(order:Order,fechaCreacion:string,fechaEntrega:string):Observable<Order> {
    const params = new HttpParams()
    .set('fechaCreacion',fechaCreacion)
    .set('fechaEntrega',fechaEntrega)

    return this.http.post<Order>(`${this.API_URL}/create`,order,{params,headers:this.headerService.headers})
  }
  updateStatus(id:number,orderStatus:string):Observable<any> {
    const params = new HttpParams()
    .set('id',id)
    .set('status',orderStatus)

    return this.http.patch<any>(`${this.API_URL}/updateStatus`,null,{params,headers:this.headerService.headers})
  }
  getOrdersByUser(userId:number):Observable<Order[]> {
    return this.http.get<Order[]>(`${this.API_URL}/getByUser`,{
      params:{user_id:userId},
      headers:this.headerService.headers
    })
  }
  getOrderById(id:number):Observable<Order> {
    return this.http.get<Order>(`${this.API_URL}/get/${id}`,{headers:this.headerService.headers})
  }
}
