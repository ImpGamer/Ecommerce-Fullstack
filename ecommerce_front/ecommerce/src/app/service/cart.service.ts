import { Injectable } from '@angular/core';
import { ItemCart } from '../class/item-cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private items:Map<number,ItemCart> = new Map<number,ItemCart>();
  itemList:ItemCart[] = []

  constructor() { }


  addItemCart(itemCart:ItemCart) {
    this.items.set(itemCart.productId,itemCart)
  }
  deleteItemCart(id:number) {
    this.items.delete(id)
    this.items.forEach((valor,clave) => console.log(`Valor: ${valor}\tClave: ${clave}`))
  }

  totalCart() {
    let total:number = 0
    this.items.forEach((item,valor) => {
      total += parseFloat(item.getTotalPrice().toFixed(2))
    })
    return total
  }

  convertFromMapToList() {
    this.itemList.splice(0,this.itemList.length)
    this.items.forEach((item) => this.itemList.push(item))
    return this.itemList
  }

  getItems() {
    return this.items
  }
}
