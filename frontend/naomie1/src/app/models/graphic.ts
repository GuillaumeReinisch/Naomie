export class Axis {
  constructor(public name: string, public formula:  string){}
}

export class Variable {
  constructor(public displayName: string, public name:  string){}
}

export class Graphic {
  //val name: String , val axis: List[String], val metadata : Map[String,String] = Map[String,String]()
  name: string;
  uid: string;
  axis: string[];
  metadata: Map<string,string>;
}

export class GraphicCollectionForm {
  constructor(public name:string, public user:string, public description : string){}
}
export class GraphicCollection {
  constructor(public name:string, public key:string,public user:string, public description : string){}
}
