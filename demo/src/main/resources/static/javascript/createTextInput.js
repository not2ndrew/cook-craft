// Minimum inputs : 0
// Maximum inputs: 10
const MIN = 0;
const MAX_INGREDIENT = 10;
const MAX_STEPS = 20;
var numOfIngredients = 0;
var numOfSteps = 0;

let ingredient_btn = document.getElementById("createIngredient");
let ul = document.getElementById("ingredient_container");
let ingredient_input = document.getElementById("ingredient_text");

let instruction_btn = document.getElementById("createInstruction");
let ol = document.getElementById("instruction_container");
let step_input = document.getElementById("step_text");

let form = document.getElementById("submit");

function CreateIngredient() {
    if (numOfIngredients >= MAX_INGREDIENT) {
        alert("Too many Ingredients! Delete some from the list.");
        return;
    }

    let text = ingredient_input.value;

    if (text.trim() === "") {
        alert("Ingredient cannot be an empty string");
        return;
    }

    let li = document.createElement("li");
    li.textContent = text;

    // Add hidden Input for Li tag
    let hiddleInput = document.createElement("input");
    hiddleInput.type = "hidden";
    hiddleInput.name = "ingredient"; // VERY IMPORTANT: This is to bind every input into Spring Boot.
    hiddleInput.value = text;

    li.appendChild(hiddleInput);

    // Delete Button 
    let deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";

    deleteBtn.addEventListener("click", function() {
        ul.removeChild(li);
        numOfIngredients--;
    });

    li.appendChild(deleteBtn);
    ul.appendChild(li);
    numOfIngredients++;

    // Clear text input after adding an ingredient
    ingredient_input.value = "";
}

function CreateInstruction() {
    if (numOfSteps > MAX_STEPS) {
        alert("Too many steps");
        return;
    }

    let text = step_input.value;

    if (text.trim() === "") {
        alert("Steps cannot be an empty string");
        return;
    }

    let li = document.createElement("li");
    li.textContent = text;

    // Add hidden Input for Li tag
    let hiddenInput = document.createElement("input");
    hiddenInput.type = "hidden";
    hiddenInput.name = "instruction";
    hiddenInput.value = text;

    li.appendChild(hiddenInput);

    // Delete Button
    let deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";

    deleteBtn.addEventListener("click", function() {
        ol.removeChild(li);
        numOfSteps--;
    });

    li.appendChild(deleteBtn);
    ol.appendChild(li);
    numOfSteps++;

    // Clear text input after adding a step
    step_input.value = "";
}



/* EVENT LISTENERS*/
ingredient_btn.addEventListener("click", function(event) {
    // This is to prevent submitting the form by accident
    event.preventDefault();
    CreateIngredient();
})

instruction_btn.addEventListener("click", function(event) {
    event.preventDefault();
    CreateInstruction();
})

form.addEventListener("submit", function() {
    if (numOfIngredients <= MIN) {
        alert("Ingredients cannot be 0!");
        return;
    }

    if (numOfSteps <= 0) {
        alert("Steps cannot be 0!");
        return;
    }

    form.submit();
})