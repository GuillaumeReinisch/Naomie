"use strict";
var NumericalData = (function () {
    function NumericalData() {
    }
    return NumericalData;
}());
exports.NumericalData = NumericalData;
var Scenario = (function () {
    function Scenario(name, uid) {
        this.name = name;
        this.uid = uid;
    }
    return Scenario;
}());
exports.Scenario = Scenario;
var Dataset = (function () {
    function Dataset(name, scenarioUid, vheaders, columns, hheaders) {
        if (name === void 0) { name = ""; }
        if (scenarioUid === void 0) { scenarioUid = ""; }
        if (vheaders === void 0) { vheaders = [""]; }
        if (columns === void 0) { columns = new Map(); }
        if (hheaders === void 0) { hheaders = ["a", "b", "c"]; }
        this.name = name;
        this.scenarioUid = scenarioUid;
        this.vheaders = vheaders;
        this.columns = columns;
    }
    return Dataset;
}());
exports.Dataset = Dataset;
exports.NUMERICALDATA = { name: 'meteo', uid: '0',
    parameters: ["T_January", "T_July", "P_January", "P_July"],
    scenario: ["Paris", "Grenoble", "Montpellier", "Toulouse", "Bordeaux"] };
//# sourceMappingURL=numerical-data.js.map