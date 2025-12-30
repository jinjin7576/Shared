<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ page session="false" %>
			<html>

			<head>
				<title>Home</title>
			</head>

			<body>
				<h1>
					Hello world!
				</h1>

				<button id='btn'>전송</button>
			</body>
			<script type="text/javascript">
				document.querySelector("#btn").addEventListener('click', () => {
					const obj = {
						tno: 1,
						owner: 'kim',
						grade: 'gold'
					};

					fetch('test/ticket.json', {
						method: 'post',
						body: JSON.stringify(obj),
						headers: {
							'Content-type': 'application/json; charset=utf-8'
						}
					})
						.then(response => response.json())
						.then(data => {
							console.log(data);
						})
						.catch(error => console.log(error));
				})
			</script>

			</html>