import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavegationHeaderComponent } from '../../navegation-header/navegation-header.component';
import { User, userType } from '../../../class/User';
import { AuthenticationService } from '../../../service/authentication.service';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,NavegationHeaderComponent,FormsModule,RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private authService:AuthenticationService,private router:Router,private toastr:ToastrService) {}

  user:User = {
    id: null,
    username: '',
    firstname: '',
    lastname: '',
    email: '',
    address: '',
    phone: '',
    password: '',
    userType: userType.USER
  }
  errorMessage:string = '';

  onSubmit(event:Event) {
    event.preventDefault()
  }

  register() {
    this.user.username = `${this.user.firstname} ${this.user.lastname}`
    this.errorMessage = ''
    
    this.authService.registerUser(this.user).subscribe((response:HttpResponse<any>) =>  {
      this.toastr.success('Tu registro se a realizado correctamente!','Registro completado')
      this.router.navigate(['user/login'])
    }
    ,(error:HttpErrorResponse) => {
      if(error.status === 226) {
        this.errorMessage = error.error.text
      }
    })
  }

}
