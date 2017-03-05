
export class NumericalData {
  name: string;
  uid: string;
  parameters : string[];
  scenario : string[];
}
export class Scenario {
  constructor( public name: string, public uid: string ) { }
}


export class Dataset {

  constructor( public name: string ="", public scenarioUid: string ="", public vheaders: string[] = [""],
               public columns: Map<string,number[]> = new Map<string,number[]>(), hheaders =  ["a","b","c"] ) {}          
}

export const NUMERICALDATA: NumericalData =
  { name: 'meteo', uid: '0',
    parameters:["T_January","T_July","P_January","P_July"],
    scenario : ["Paris","Grenoble","Montpellier","Toulouse", "Bordeaux"]};
