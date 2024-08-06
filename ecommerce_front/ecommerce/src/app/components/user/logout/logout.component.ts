import { Component, OnInit } from '@angular/core';
import { SessionStorageService } from '../../../service/session-storage.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [],
  templateUrl: './logout.component.html'
})
export class LogoutComponent implements OnInit {
  constructor(private sessionStorage:SessionStorageService,private router:Router,private toastr:ToastrService) {}

  ngOnInit(): void {
    console.log(`Token que se tenia: ${this.sessionStorage.getItem('token')}`)
    this.sessionStorage.removeItem('token')
    this.sessionStorage.removeItem('userId')
    this.toastr.info('Has salido de sesion correctamente','Salida de Sesion')
    this.router.navigate(['/'])
  }
}
