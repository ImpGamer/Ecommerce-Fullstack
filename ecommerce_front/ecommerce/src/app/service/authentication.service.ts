import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User, UserCredentials } from '../class/User';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private API_URL:string = "http://localhost:8085/security/user"

  constructor(private http:HttpClient) { }

  registerUser(user:User):Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.API_URL}/register`,user)
  }

  loginUser(credentials:UserCredentials):Observable<any> {
    return this.http.post<any>(`${this.API_URL}/login`,credentials)
  }
}
