export class CollectionForm {
  constructor(public name:string, public userName:string, public description : string, public creationDate:string, public namespace: string = ""){}
}
export class Collection {
  constructor(public name:string, public key:string,public user:string, public description : string,  public creationDate:string, public namespace: string = ""){}
}
