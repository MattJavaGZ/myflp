// Rozwijane MENU
const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");

function toggleMenu() {
    if (menu.classList.contains("expanded")) {
        menu.classList.remove("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars"></i>';
    } else {
        menu.classList.add("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="fa-solid fa-bars"></i>';
    }
}

toggle.addEventListener("click", toggleMenu, false);

// -----------------
// Tworzenie nowego zamowienia
let orderItems = [];
let selectedProduct = null;

document.getElementById("productSearch").addEventListener("input", function () {
    const query = this.value.trim();
    const suggestionsDiv = document.getElementById("suggestions");

    if (query.length < 2) {
        suggestionsDiv.innerHTML = "";
        selectedProduct = null;
        return;
    }

    fetch(`/produkty/szukaj-podpowiedz?word=${encodeURIComponent(query)}`)
        .then(res => res.json())
        .then(data => {
            suggestionsDiv.innerHTML = "";
            data.forEach(prod => {
                const div = document.createElement("div");
                div.textContent = `${prod.name} - ${parseFloat(prod.retailPriceBrutto).toFixed(2)} zł`;
                div.style.cursor = "pointer";
                div.style.padding = "5px";
                div.addEventListener("click", () => {
                    selectedProduct = prod;
                    document.getElementById("productSearch").value = prod.name;
                    suggestionsDiv.innerHTML = "";
                });
                suggestionsDiv.appendChild(div);
            });
        });
});

function addSelectedProduct() {
    if (!selectedProduct) {
        alert("Wybierz produkt z listy.");
        return;
    }
    const qtyInput = document.getElementById("productQuantity");
    let quantity = parseInt(qtyInput.value);
    if (isNaN(quantity) || quantity < 1) {
        alert("Podaj prawidłową ilość.");
        return;
    }

    const existing = orderItems.find(p => p.id === selectedProduct.id);
    if (existing) {
        existing.quantity += quantity;
    } else {
        orderItems.push({
            id: selectedProduct.id,
            name: selectedProduct.name,
            retailPriceBrutto: parseFloat(selectedProduct.retailPriceBrutto),
            purchasePriceBrutto: parseFloat(selectedProduct.purchasePriceBrutto),
            cc: parseFloat(selectedProduct.cc) || 0,
            quantity: quantity,
            discount: 0
        });
    }

    selectedProduct = null;
    document.getElementById("productSearch").value = "";
    qtyInput.value = 1;
    renderOrderTable();
}

function renderOrderTable() {
    const tbody = document.querySelector("#orderTable tbody");
    tbody.innerHTML = "";

    let totalProfit = 0;
    let totalBrutto = 0;
    let totalCC = 0;

    orderItems.forEach((item, index) => {
        const priceAfterDiscount = item.retailPriceBrutto * (1 - item.discount / 100);
        const totalSalePrice = priceAfterDiscount * item.quantity;
        const profit = (priceAfterDiscount - item.purchasePriceBrutto) * item.quantity;
        const ccPoints = item.cc * item.quantity;

        totalProfit += profit;
        totalBrutto += totalSalePrice;
        totalCC += ccPoints;

        const tr = document.createElement("tr");
        tr.innerHTML = `
        <td data-label="Produkt">${item.name}</td>
        <td data-label="Cena katalog szt">${item.retailPriceBrutto.toFixed(2)}</td>
        <td data-label="Twoja cena zakupu szt">${item.purchasePriceBrutto.toFixed(2)}</td>
        <td data-label="Cena po rabacie szt">${priceAfterDiscount.toFixed(2)}</td>
        <td data-label="Ilość">
            <input type="number" min="1" value="${item.quantity}" onchange="updateQuantity(${index}, this.value)">
        </td>
        <td data-label="Rabat">
            <select onchange="updateDiscount(${index}, this.value)">
                <option value="0"  ${item.discount === 0 ? "selected" : ""}>0%</option>
                <option value="5"  ${item.discount === 5 ? "selected" : ""}>5%</option>
                <option value="10" ${item.discount === 10 ? "selected" : ""}>10%</option>
                <option value="15" ${item.discount === 15 ? "selected" : ""}>15%</option>
                <option value="20" ${item.discount === 20 ? "selected" : ""}>20%</option>
                <option value="25" ${item.discount === 25 ? "selected" : ""}>25%</option>
                <option value="30" ${item.discount === 30 ? "selected" : ""}>30%</option>
            </select>
        </td>
        <td data-label="Łączna cena">${totalSalePrice.toFixed(2)}</td>
        <td data-label="Pkt CC">${ccPoints.toFixed(3)}</td>
        <td data-label="Zysk">${profit.toFixed(2)}</td>
        <td data-label="Akcja">
            <button type="button" onclick="removeItem(${index})">X</button>
        </td>
    `;

        tbody.appendChild(tr); // <---- tego brakowało!
    });

    document.getElementById("totalProfit").textContent = totalProfit.toFixed(2);
    document.getElementById("totalBrutto").textContent = totalBrutto.toFixed(2);
    document.getElementById("totalCC").textContent = totalCC.toFixed(3);
}

function updateQuantity(index, value) {
    orderItems[index].quantity = parseInt(value) || 1;
    renderOrderTable();
}

function updateDiscount(index, value) {
    orderItems[index].discount = parseInt(value) || 0;
    renderOrderTable();
}

function removeItem(index) {
    orderItems.splice(index, 1);
    renderOrderTable();
}

function prepareOrderData() {
    if (orderItems.length === 0) {
        alert("Dodaj co najmniej jeden produkt do zamówienia.");
        return false;
    }
    document.getElementById("orderItemsJson").value = JSON.stringify(orderItems);
    return true;
}