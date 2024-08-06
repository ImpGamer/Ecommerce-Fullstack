import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SessionStorageService } from './session-storage.service';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private token:string = ''
  public headers:HttpHeaders = new HttpHeaders

  constructor(private sessionStorage:SessionStorageService) { 
    this.token = this.sessionStorage.getItem('token')

    this.headers = new HttpHeaders({
      //'Content-Type':'application/json',
      'Authorization':this.token
    })
  }
}
