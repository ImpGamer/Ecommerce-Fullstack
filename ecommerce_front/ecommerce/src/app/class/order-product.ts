import { Product } from "./product";

export class OrderProduct {
    constructor(
        public id:number|null,
        public cantidad:number,
        public price:number,
        public product:Product
    ) {}
}