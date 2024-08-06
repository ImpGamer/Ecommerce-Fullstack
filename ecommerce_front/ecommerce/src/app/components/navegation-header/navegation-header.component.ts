import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SessionStorageService } from '../../service/session-storage.service';

@Component({
  selector: 'app-navegation-header',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './navegation-header.component.html'
})
export class NavegationHeaderComponent implements OnInit {
  isLogin:boolean = false;

  constructor(private sessionStorage:SessionStorageService) {}
  ngOnInit(): void {
    const token:string = this.sessionStorage.getItem('token')
    this.isLogin = token.length !== 0 || token === undefined ?true:false
  }

  
}
