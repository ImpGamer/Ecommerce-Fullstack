import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { Product } from '../../class/product';
import { CommonModule } from '@angular/common';
import { ProductCartComponent } from '../product/productCart.component';
import { NavegationHeaderComponent } from '../navegation-header/navegation-header.component';
import { HomeService } from '../../service/home.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule,ProductCartComponent,NavegationHeaderComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products:Product[] = []
  
  constructor(private service:HomeService) {}

  //Funcion que se ejecutara inmediatamente al cargar el componente
  ngOnInit(): void {
    this.service.getProducts().subscribe(
      data => {
        this.products = data
        console.log(data)
      }

    )
  }
  
}
