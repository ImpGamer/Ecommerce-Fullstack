import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DataPayment } from '../class/DataPayment';
import { Observable } from 'rxjs';
import { PayPalResponse } from '../class/PayPal-response';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private API_URL:string = "http://localhost:8085/payments"

  constructor(private http:HttpClient,private headersService:HeaderService) { }

  getUrlPayPalPayment(dataPayment:DataPayment):Observable<PayPalResponse> {
    return this.http.post<PayPalResponse>(`${this.API_URL}/doing`,dataPayment,{headers:this.headersService.headers})
  }
}
