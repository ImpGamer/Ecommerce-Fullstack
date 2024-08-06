import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductService } from '../../service/product.service';
import { Product } from '../../class/product';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';
import { FormsModule } from '@angular/forms';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { Category } from '../../class/category';
import { CategoryService } from '../../service/category.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule,RouterLink,AdminHeaderComponent,FormsModule,ToastrModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {
  producto:Product = {
    id:0,
    code: '00015',
    name:'',
    description:'',
    price:0,
    url_image:'',
    categories: [],
    Image: new File([],'empty.txt')
  }
  titleForm:string = ''
  capture_id:number = 0
  categoriesAvailable:Category[] = []

  constructor(private service:ProductService,private route:ActivatedRoute,private router:Router,private toastr:ToastrService,private categoryService:CategoryService) {}

  ngOnInit(): void {
    this.route.params.subscribe(
      params => this.capture_id = params['id']
    )

    this.getCategories()
    if(this.capture_id == 0) {
      this.titleForm = 'Crear Producto'
    } else {
      this.titleForm = 'Actualizar Datos Producto'
      this.getProductId(this.capture_id)
    }
  }

  getProductId(productID:number) {
    this.service.getProductById(productID).subscribe(
      data => {
        this.producto = data
        this.producto.Image = new File([],'empty.txt')
      }
    )
  }

  getCategories() {
    this.categoryService.getCategories().subscribe(data => this.categoriesAvailable = data)
  }

  addProduct() {
    const URL_IMAGE:string = "http://localhost:8085/images"

    this.producto.url_image = `${URL_IMAGE}/${this.producto.Image.name}`
    if(this.capture_id != 0) {
      this.producto.id = this.capture_id
      this.service.updateProduct(this.producto.id,this.producto,this.producto.Image).subscribe(
        data => {
          console.log(`Producto actualizado correctamente \n${data}`)
          this.toastr.info('Producto Actualizado correctamente','Productos')
        }
      )
    } else {
      this.service.createProduct(this.producto,this.producto.Image).subscribe(
        data => {
          this.toastr.success('Producto Registrado correctamente','Productos')
          console.log(`Producto guardado correctamente \n${data}`)
        }
        
      )
    }
    this.router.navigate(['/admin'])
  }

  onFileChange(event:any) {
    const file:File = event.target.files[0]
    if(file) {
      this.producto.Image = file
      console.log(this.producto.Image)
    }
  }

}
