function fillCompareFields(p_values, p_source, cmpVals) {
	if (p_source == 0) {
		if (cmpVals[0] < 0) {
			cmpVals[0] = parseInt(p_values[0]) * parseFloat(p_values[1]);
		}
	} else if (p_source == 1) {
		if (cmpVals[1] < 0) {
			cmpVals[1] = parseInt(p_values[0]) * parseFloat(p_values[1]);
		}
	}

	if (cmpVals[0] > 0 && cmpVals[1] > 0) {
		cmpVals[2] = Math.abs(cmpVals[1] - cmpVals[0]);
	}
};

function preCheckForTopAsin(cmpVals) {
	return cmpVals[2] > 0.001;
};

function compareForTopAsin(p_bindings, cmpVals) {
	var compareDiffRet = compareDiff(p_bindings, cmpVals);
	return compareDiffRet;
};

function compareForOutputSort(p_bindings, cmpVals) {
	return -1 * compareDiff(p_bindings, cmpVals);
};

function compareDiff(p_bindings, cmpVals) {
	var numDiff = parseFloat(p_bindings.get('diff'));
	var cmpDiff = cmpVals[2] - numDiff;
	if (cmpDiff > -0.001 && numDiff < 0.001) {
		return 1;
	}
	if (cmpDiff > 0) {
		return 1;
	}
	return -1;
}

function getCompareDiffDisplayValue(cmpVals) {
	return cmpVals[1] - cmpVals[0];
};

function getCompareOrigDisplayValue(cmpVals) {
	return cmpVals[0];
};

function getCompareNewDisplayValue(cmpVals) {
	return cmpVals[1];
};

