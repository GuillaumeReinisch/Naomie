
export class NumericalData {
  name: string;
  uid: string;
  parameters : string[];
  scenario : string[];
}

export const NUMERICALDATA: NumericalData =
  { name: 'meteo', uid: '0',
    parameters:["T_January","T_July","P_January","P_July"],
    scenario : ["Paris","Grenoble","Montpellier","Toulouse", "Bordeaux"]};
