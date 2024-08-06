import { CommonModule, NumberFormatStyle } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavegationHeaderComponent } from '../../navegation-header/navegation-header.component';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../../class/product';
import { ProductService } from '../../../service/product.service';
import { CartService } from '../../../service/cart.service';
import { FormsModule } from '@angular/forms';
import { ItemCart } from '../../../class/item-cart';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../../../service/authentication.service';
import { HomeService } from '../../../service/home.service';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [CommonModule,NavegationHeaderComponent,FormsModule],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.css'
})
export class DetailProductComponent implements OnInit {
  producto:Product = {
    id: 0,
    name: '',
    code: '',
    description: '',
    price: 0,
    url_image: '',
    categories: [],
    Image: new File([],'empty.txt')
  }
  id:number=0;
  quantity:number=1;

  constructor(private route:ActivatedRoute,private homeService:HomeService,private cartService:CartService,
    private toastr:ToastrService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.id = +params['id']; // El + convierte el string a número
    });
    this.getProductById()
  }

  getProductById() {
    this.homeService.getProductById(this.id).subscribe(
      product => this.producto = product
    )
  }

  addCart(id:number) {
    const itemCart = new ItemCart(id,this.producto.name,this.quantity,this.producto.price)

    this.cartService.addItemCart(itemCart)
    console.log(this.cartService.getItems())
    this.toastr.success('Producto agregado al carrito!','Producto agregado')
  }

}
