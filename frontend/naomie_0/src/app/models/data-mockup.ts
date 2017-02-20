

export class Graphic {
  //val name: String , val axis: List[String], val metadata : Map[String,String] = Map[String,String]()
  name: string;
  uid: string;
  axis: string[];
  metadata: Map<string,string>;
}

export const GRAPHS: Graphic[] = [
  { name: 'T_July vs T_January', uid: '0', axis:["T_July","T_January"], metadata: new Map<string,string>()},
  { name: 'T_July/T_January vs P_July/P_January', uid: '1', axis:["T_July/T_January","P_July/P_January"], metadata: new Map<string,string>()}
];
