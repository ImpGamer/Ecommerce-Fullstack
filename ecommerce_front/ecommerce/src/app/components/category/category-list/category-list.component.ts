import { Component, OnInit } from '@angular/core';
import { Category } from '../../../class/category';
import { CategoryService } from '../../../service/category.service';
import { CommonModule } from '@angular/common';
import { AdminHeaderComponent } from '../../admin-header/admin-header.component';
import { RouterLink } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule,AdminHeaderComponent,RouterLink,ToastrModule],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit {
  categories:Category[] = []
  constructor(private service:CategoryService) {}

  ngOnInit(): void {
    this.getCategories()
  }

  getCategories() {
    this.service.getCategories().subscribe(data => this.categories = data)
  }

  deleteCategory(id:number) {
    Swal.fire({
      title:'Estas seguro?',
      text:'Que deseas eliminar esta categoria',
      icon:'warning',
      showCancelButton:true,
      confirmButtonColor:'##1dab29',
      confirmButtonText:'Si, Eliminar!',
      cancelButtonColor:'#ff0000',
      cancelButtonText:'Cancelar'
    }).then((result) => {
      if(result.isConfirmed) {
        this.service.deleteCategory(id).subscribe(() => this.getCategories())
        Swal.fire('Eliminado','El Producto a sido eliminado correctamente','success')
      }
    })
  }

}
