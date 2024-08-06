import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../class/category';
import { Observable } from 'rxjs';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private API_URL:string = "http://localhost:8085/admin/categories"

  constructor(private http:HttpClient,private headerService:HeaderService) { }

  getCategories():Observable<Category[]> {
    return this.http.get<Category[]>(this.API_URL,{headers:this.headerService.headers})
  }
  createCategory(category:Category):Observable<Category> {
    return this.http.post<Category>(`${this.API_URL}/create`,category,{headers:this.headerService.headers})
  }
  deleteCategory(id:number):Observable<string> {
    return this.http.delete<string>(`${this.API_URL}/${id}`,{headers:this.headerService.headers})
  }
  getCategoryById(id:number):Observable<Category> {
    return this.http.get<Category>(`${this.API_URL}/${id}`,{headers:this.headerService.headers})
  }
}
