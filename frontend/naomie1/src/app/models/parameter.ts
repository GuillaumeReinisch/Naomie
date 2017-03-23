

export class ParameterForm {
  constructor(public formula:string, public variables : Map<string,string>,
     public userName:string, public creationDate:string, public collection:string){}
}
export class Parameter {
  constructor(public formula:string, public key:string, public variables : Map<string,string>, public userName:string, public creationDate:string){}
}
export class ParameterSummary {
  constructor(public formula:string, public variables : Map<string,string>){}
}
