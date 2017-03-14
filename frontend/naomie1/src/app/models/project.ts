
export class Project {

  public scenarios : string[];

  constructor(public name: string, public key: string, public userName: string,
    public creationDate: string, public summary:string,
    scenarioConcat : string){

      this.scenarios = scenarioConcat.split(" ")
    };
}

export class ProjectForm {
  constructor(public name: string, public userName: string, public creationDate: string, public summary:string){};
}
