import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AdminHeaderComponent } from '../../admin-header/admin-header.component';
import { Category } from '../../../class/category';
import { CategoryService } from '../../../service/category.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [CommonModule,AdminHeaderComponent,FormsModule],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent implements OnInit{
  categoria:Category = {
    id: 0,
    name: ''
  }
  titleForm:string = ''

  constructor(private service:CategoryService,private toastr:ToastrService, private router:Router,private route:ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(
      params => this.categoria.id = params['id']
    )
    this.titleForm = this.categoria.id == 0?'Crear Categoria':'Editar Categoria'
    if(this.categoria.id === 0) {
      this.categoria.id = NaN
    } else {
      this.getCategoryId()
    }
  }

  getCategoryId() {
    this.service.getCategoryById(this.categoria.id).subscribe(data => this.categoria = data)
  }

  saveCategory() {
    this.service.createCategory(this.categoria).subscribe(
      data => {
        console.log(this.categoria)
        if(isNaN(this.categoria.id) || this.categoria.id == 0) {
          this.createToastr('Categoria creada',`La categoria ${data.name} ha sido creada correctamente`)
        } else {
          this.createToastr('Categoria editada',`La categoria ha sido modificada por '${data.name}' correctamente`)
        }
        this.router.navigate(['/admin/categories'])
      }
    )
  }

  createToastr(title:string,text:string) {
    const toastrOptions = {
      timeOut: 7000, // Tiempo en milisegundos (5000 ms = 5 segundos)
      closeButton: true, // Agregar un botón de cerrar
      progressBar: true, // Mostrar una barra de progreso
      positionClass: 'toast-top-right', // Posición del Toastr en la pantalla
    }

    const toastr = this.toastr.success(text,title,toastrOptions)
    return toastr
  }

}
