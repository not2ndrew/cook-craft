const MIN = 0;
const MAX_INGREDIENT = 10;
const MAX_STEPS = 20;

let numOfIngredients = 0;
let numOfSteps = 0;

// Ingredient Elements
const ingredientBtn = document.getElementById("createIngredient");
const ingredientList = document.getElementById("ingredient-container");
const ingredientInput = document.getElementById("ingredient-text");
const unitSelect = document.getElementById("unit-select");
const amountInput = document.getElementById("amount-input");

// Instruction Elements
const instructionBtn = document.getElementById("createInstruction");
const instructionList = document.getElementById("instruction-container");
const step_input = document.getElementById("step-text");

const form = document.getElementById("submit");

// Create Custom Unit Input
unitSelect.addEventListener("change", (event) => {
    const selectedValue = event.target.value;

    let existingInput = document.getElementById("customUnitInput");

    if (selectedValue == "custom") {
        if (!existingInput) {
            const input = document.createElement("input");
            input.type = "text";
            input.name = "ingredientUnit";
            input.className = "text-input"
            input.id = "customUnitInput";
            input.placeholder = "Enter Custom Unit";
        
            unitSelect.insertAdjacentElement("afterend", input);
        }
    } else {
        if (existingInput) {
            existingInput.remove();
        }
    }


})

// Create Hidden Input
const createHiddenInput = (name, value) => {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = name;
    input.value = value;
    return input;
}

// Create Delete Button
const createDeleteBtn = (parentList, itemElement, counterCallBack) => {
    const btn = document.createElement("button");
    btn.textContent = "Delete";
    btn.addEventListener("click", () => {
        parentList.removeChild(itemElement);
        counterCallBack();
    })

    return btn;
}

// Create Ingredient
const createIngredient = () => {
    if (numOfIngredients >= MAX_INGREDIENT) {
        alert("Too many Ingredients! Delete some from the list.");
        return;
    }

    const name = ingredientInput.value.trim();
    let unit = unitSelect.value.trim();
    const amount = amountInput.value.trim();

    if (unit == "custom") {
        const customInput = document.getElementById("customUnitInput");
        if (customInput) {
            unit = customInput.value.trim();
        }

        if (!unit) {
            alert("Please enter a Custom Unit");
            return;
        }
    }

    if (!name || !unit) {
        alert("Ingredient cannot be empty");
        return;
    }

    const li = document.createElement("li");
    li.textContent = `${amount} ${unit} ${name}`;

    li.appendChild(createHiddenInput("ingredientName", name));
    li.appendChild(createHiddenInput("ingredientUnit", unit));
    li.appendChild(createHiddenInput("ingredientAmount", amount));
    li.appendChild(createDeleteBtn(ingredientList, li, () => numOfIngredients--));

    ingredientList.appendChild(li);
    numOfIngredients++;

    ingredientInput.value = "";
    amountInput.value = "";
}

// Create Instruction
const createInstruction = () => {
    if (numOfSteps >= MAX_STEPS) {
        alert("Too many steps");
        return;
    }

    const text = step_input.value.trim();

    if (!text) {
        alert("Steps cannot be empty");
        return;
    }

    const li = document.createElement("li");

    li.textContent = `${text}`;

    li.appendChild(createHiddenInput("instruction", text));
    li.appendChild(createDeleteBtn(instructionList, li, () => numOfSteps--));

    instructionList.appendChild(li);
    numOfSteps++;

    step_input.value = "";
}

// Event Listeners
ingredientBtn.addEventListener("click", (e) => {
    e.preventDefault();
    createIngredient();
});

instructionBtn.addEventListener("click", (e) => {
    e.preventDefault();
    createInstruction();
});

form.addEventListener("submit", (e) => {
    if (numOfIngredients <= MIN) {
        e.preventDefault();
        alert("Ingredients cannot be 0!");
        return;
    }

    if (numOfSteps <= MIN) {
        e.preventDefault();
        alert("Steps cannot be 0!");
    }
});