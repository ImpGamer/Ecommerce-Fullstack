import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NavegationHeaderComponent } from '../../navegation-header/navegation-header.component';
import { ItemCart } from '../../../class/item-cart';
import { CartService } from '../../../service/cart.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../service/user.service';
import { User, userType } from '../../../class/User';
import { Order, OrderStatus } from '../../../class/order';
import { OrderProduct } from '../../../class/order-product';
import { Product } from '../../../class/product';
import { ProductService } from '../../../service/product.service';
import { firstValueFrom } from 'rxjs';
import { OrderService } from '../../../service/order.service';
import { PaymentService } from '../../../service/payment.service';
import { DataPayment } from '../../../class/DataPayment';
import { PayPalResponse } from '../../../class/PayPal-response';
import { SessionStorageService } from '../../../service/session-storage.service';

@Component({
  selector: 'app-sumary-order',
  standalone: true,
  imports: [CommonModule, RouterLink, NavegationHeaderComponent, FormsModule],
  templateUrl: './sumary-order.component.html',
  styleUrl: './sumary-order.component.css',
})
export class SumaryOrderComponent implements OnInit {
  items: ItemCart[] = [];
  totalCart: number = 0;
  usuario: User = {
    id: 0,
    username: '',
    firstname: '',
    lastname: '',
    email: '',
    address: '',
    phone: '',
    password: '',
    userType: userType.USER,
  };
  private userId:number = 0

  constructor(
    private cartService: CartService,
    private userService: UserService,
    private productService: ProductService,
    private orderService:OrderService,
    private paypalService:PaymentService,
    private storageService:SessionStorageService
  ) {}

  ngOnInit(): void {
    this.items = this.cartService.convertFromMapToList();
    this.totalCart = parseFloat(this.cartService.totalCart().toFixed(2));
    this.userId = this.storageService.getItem('userId')
    this.getUserById(this.userId);
  }

  deleteItem(productId: number) {
    this.cartService.deleteItemCart(productId);
    this.items = this.cartService.convertFromMapToList();
    this.totalCart = parseFloat(this.cartService.totalCart().toFixed(2));
  }

  async createOrder() {
    let orderProducts: OrderProduct[] = [];
    for(let item of this.items) {
      const producto:Product = await this.getProductById(item.productId)
      orderProducts.push(this.mapItemCartToOrderProduct(item,producto))
    }

    const fechaEntrega = new Date()
    fechaEntrega.setDate(fechaEntrega.getDate()+1)

    const formattedCreacion = new Date().toISOString().split('T')[0]
    const formattedEntrega = fechaEntrega.toISOString().split('T')[0]

    const orden: Order = new Order(
      null,
      this.usuario,
      OrderStatus.CANCELED,
      orderProducts
    );
    this.orderService.createOrder(orden,formattedCreacion,formattedEntrega).subscribe({
      next: (data) => {
        this.storageService.setItem("orden",data)
        console.log(`Orden almacenada en la session Storage ${this.storageService.getItem("orden")}`)
      },
      error: error => console.log(`Error al crear la orden ${error}`)
    })

    let urlPayment:PayPalResponse = {url:''};
    const dataPayment = new DataPayment(
      'Paypal',
      this.totalCart.toString(),
      'USD',
      'COMPRA'
    )
    this.paypalService.getUrlPayPalPayment(dataPayment).subscribe(url => {
      urlPayment = url
      console.log(`Redireccionando a ${url}`)
      window.location.href = urlPayment.url
    })
  }

  private mapItemCartToOrderProduct(itemCart: ItemCart,producto:Product) {
    const orderProduct: OrderProduct = new OrderProduct(
      null,
      itemCart.quantity,
      itemCart.getTotalPrice(),
      producto
    );

    return orderProduct;
  }

  private getProductById(productId: number): Promise<Product> {
    return firstValueFrom(this.productService.getProductById(productId));
  }

  getUserById(userId: number) {
    this.userService
      .getClientById(userId)
      .subscribe((data) => (this.usuario = data));
  }
}
