function addToCart(bookId) {
    if (confirm("장바구니에 해당 도서를 추가하시겠습니까?")) {
        // 맨 앞에 슬래시(/)를 붙여 절대 경로로 요청
        fetch('/BookMarket/cart/book/' + bookId, {
            method: 'PUT'
        })
            .then(response => {
                // 200 OK 또는 204 NO_CONTENT 모두 성공 처리
                if (response.ok || response.status === 204) {
                    // 담기 성공 시 장바구니 페이지로 이동
                    window.location.href = '/BookMarket/cart';
                } else {
                    alert('장바구니 추가에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다.');
            });
    }
}