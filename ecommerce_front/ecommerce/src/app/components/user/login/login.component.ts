import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavegationHeaderComponent } from '../../navegation-header/navegation-header.component';
import { Router, RouterLink } from '@angular/router';
import { UserCredentials } from '../../../class/User';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../../service/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { SessionStorageService } from '../../../service/session-storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,NavegationHeaderComponent,RouterLink,FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private authService:AuthenticationService,private sessionStorage:SessionStorageService,private router:Router) {}
  credentials:UserCredentials = {
    email: '',
    password: ''
  }
  errorMessage =''

  login() {
    this.authService.loginUser(this.credentials).subscribe(
      token => {
        const dataUser = token
        this.sessionStorage.setItem('token',dataUser.token)
        this.sessionStorage.setItem('userId',parseInt(dataUser.id))
        
        switch(dataUser.type) {
          case 'ADMIN': this.router.navigate(['/admin']); break
          case 'USER': this.router.navigate(['/'])
        }
      },
      (error: HttpErrorResponse) => this.errorMessage = error.error.Message
    )
  }

}
