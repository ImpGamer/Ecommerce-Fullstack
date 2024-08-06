import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CommonModule } from '@angular/common';
import { AdminProductComponent } from './components/admin-product/admin-product.component';

@Component({
  //Nombre del componente
  selector: 'app-root',
  standalone: true,
  //Importacion de componentes
  imports: [RouterOutlet,HomeComponent,CommonModule,AdminProductComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ecommerce';
}