import { OrderProduct } from "./order-product";
import { User } from "./User";

export class Order {
    constructor(
        public id:number|null,
        public user:User,
        public orderStatus:OrderStatus,
        public orderProducts:OrderProduct[]
    ){}

    getTotal() {
        let total:number=0
        for(let orderPro of this.orderProducts) {
            total += orderPro.price * orderPro.cantidad
            console.log(total)
        }

    }
}

export enum OrderStatus {
    CANCELED,
    CONFIRMED
}