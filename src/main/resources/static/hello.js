function hljsClick() {
	const code = document.querySelector('#codeBlock');
	hljs.highlightBlock(code);
}

function handleKeydown(event) {
	// TAB Process
	if (event.keyCode === 9) {
		const code = document.querySelector('#codeBlock');
		let caret = document.createElement('span');
		caret.id = '__caret';
		window.getSelection().getRangeAt(0).insertNode(caret);
		code.blur();
		code.innerHTML = code.innerHTML.replace('<span id="__caret">', '&nbsp;&nbsp;&nbsp;&nbsp;<span id="__caret">');
		code.focus();
		
		const range = document.createRange();
		caret = document.getElementById('__caret');
		range.selectNode(caret);
		const selection = window.getSelection();
		selection.removeAllRanges();
		selection.addRange(range);
		range.deleteContents();
		
		event.preventDefault();
		return false;
	}
}

async function send() {
	let source = document.querySelector('#codeBlock').innerText;
	// remove word-wrap space
	source = source.replaceAll('\u00A0', '\u0020');
	
	// remove duration
	document.querySelector('#duration').innerText = '';
	
	const res = await fetch('/check', {
		method: 'post',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({code: source})
	});
	
	if (res.ok) {
		const data = await res.json();
		document.querySelector('#result').value = data.result;
		document.querySelector('#duration').innerText = data.duration + 'ms';
	}
	else {
		console.error(res.status);
	}
}