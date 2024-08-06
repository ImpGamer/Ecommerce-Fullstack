import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../class/User';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private URL_API:string = "http://localhost:8085/users"

  constructor(private http:HttpClient,private headersService:HeaderService) { }

  getClientById(id:number):Observable<User> {
    return this.http.get<User>(`${this.URL_API}/${id}`,{headers:this.headersService.headers})
  }
}
