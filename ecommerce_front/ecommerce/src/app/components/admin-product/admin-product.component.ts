import { Component, OnInit } from '@angular/core';
import { Product } from '../../class/product';
import { ProductService } from '../../service/product.service';
import { CommonModule } from '@angular/common';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-product',
  standalone: true,
  imports: [RouterOutlet,CommonModule,AdminHeaderComponent,RouterLink],
  templateUrl: './admin-product.component.html',
  styleUrl: './admin-product.component.css'
})
export class AdminProductComponent implements OnInit {

  products:Product[] = []
  constructor(private service:ProductService) {}

  ngOnInit(): void {
    this.getProducts()
  }

  getProducts() {
    this.service.getProducts().subscribe(
      data => {
        this.products = data
        console.log(data)
      }
    )
  }

  deleteProductById(id:number) {
    Swal.fire({
      title:'Estas seguro?',
      text:'Que deseas eliminar este producto',
      icon:'warning',
      showCancelButton:true,
      confirmButtonColor:'##1dab29',
      confirmButtonText:'Si, Eliminar!',
      cancelButtonColor:'#ff0000',
      cancelButtonText:'Cancelar'
    }).then((result) => {
      if(result.isConfirmed) {
        this.service.deleteProduct(id).subscribe(() => this.getProducts())
        Swal.fire('Eliminado','El Producto a sido eliminado correctamente','success')
      }
    })
  }

}
