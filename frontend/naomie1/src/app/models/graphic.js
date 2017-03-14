"use strict";
var Axis = (function () {
    function Axis(name, formula) {
        this.name = name;
        this.formula = formula;
    }
    return Axis;
}());
exports.Axis = Axis;
var Variable = (function () {
    function Variable(displayName, name) {
        this.displayName = displayName;
        this.name = name;
    }
    return Variable;
}());
exports.Variable = Variable;
//# sourceMappingURL=graphic.js.map