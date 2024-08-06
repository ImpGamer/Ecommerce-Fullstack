import { Component, OnInit } from '@angular/core';
import { NavegationHeaderComponent } from '../../navegation-header/navegation-header.component';
import { CommonModule } from '@angular/common';
import { SessionStorageService } from '../../../service/session-storage.service';
import { OrderService } from '../../../service/order.service';
import { Order, OrderStatus } from '../../../class/order';

@Component({
  selector: 'app-payment-success',
  standalone: true,
  imports: [NavegationHeaderComponent,CommonModule],
  templateUrl: './payment-success.component.html',
  styleUrl: './payment-success.component.css'
})
export class PaymentSuccessComponent implements OnInit {
  constructor(private storageService:SessionStorageService,
    private orderService:OrderService
  ) {}

  ngOnInit(): void {
    let orden:Order = this.storageService.getItem("orden")

    if(orden.id != null) {
      this.orderService.updateStatus(orden.id,'CONFIRMED').subscribe(data => console.log(data))
    }
  }

}
