// public/script.js
function searchProducts() {
  const query = document.getElementById('searchInput').value;
  fetch(`/search?query=${encodeURIComponent(query)}`)
    .then(res => res.json())
    .then(data => {
      const resultsDiv = document.getElementById('results');
      resultsDiv.innerHTML = '';

      if (data.length === 0) {
        resultsDiv.innerHTML = '<p>No products found.</p>';
        return;
      }

      data.forEach(product => {
        const div = document.createElement('div');
        div.className = 'product';
        div.innerHTML = `
          <strong>${product.name}</strong><br>
          Price: $${product.price}<br>
          Rating: ${product.rating}
        `;
        resultsDiv.appendChild(div);
      });
    })
    .catch(err => {
      console.error('Search error:', err);
    });
}
