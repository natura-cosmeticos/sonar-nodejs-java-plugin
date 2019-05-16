const dependenciesMetricLabels = {
	number_of_dependencies: "dependencies",
	number_of_dev_dependencies: "devDependencies"
}

window.registerExtension('nodejs/dependencies_report', options => {
	var isDisplayed = true;

	let get_number_of_dependencies = window.SonarRequest.getJSON('/api/measures/component', {
		resolved: false,
		componentKey: options.component.key,
		metricKeys: "number_of_dependencies"
	});		
	let get_number_of_dev_dependencies = window.SonarRequest.getJSON('/api/measures/component', {
		resolved: false,
		componentKey: options.component.key,
		metricKeys: "number_of_dev_dependencies"
	});

	let promises = [get_number_of_dependencies, get_number_of_dev_dependencies].map(p => p.catch(() => null));
	Promise.all(promises).then(responses => {
		if (isDisplayed) {
			let dependenciesFound = false;
			let header = document.createElement('h2');
			header.textContent = 'NodeJS dependencies report';
			options.el.appendChild(header);
			responses.forEach(res => {
				if (res == null)
					return;
				let measures = res.component.measures;
				if (measures.length > 0) {
					dependenciesFound = true;
					let txt = document.createElement('p');
					txt.textContent = `The project ${options.component.key} has ${measures[0].value} ${dependenciesMetricLabels[measures[0].metric]}`;
					options.el.appendChild(txt);
				}
			});
			if (!dependenciesFound) {
				let txt = document.createElement('p');
				txt.textContent = `The project does not have nodeJS dependencies`;
				options.el.appendChild(txt);
			}
		}
	});

	return () => {
		isDisplayed = false;
	};
});
	