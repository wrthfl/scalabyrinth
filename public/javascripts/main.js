const d = document

let gridSizeSpan = d.getElementById("gridSize")
let gridCellSizeInput = d.getElementById("size")

gridCellSizeInput.addEventListener("input", function (e) {

    gridSizeSpan.innerHTML = gridCellSizeInput.value;
})

function move(sibling) {
    if (sibling != null) {
        active.classList.toggle("active")
        sibling.focus();
        sibling.classList.toggle("active")
        active = sibling;
    }
}


function markCell(cell) {
    d.querySelectorAll("#labyrinthGrid td").forEach(el => el.classList.remove("marked"))
    cell.classList.toggle("marked")
}

// d.getElementById("labyrinthGrid").addEventListener("click",)
d.querySelectorAll("#labyrinthGrid td").forEach(e => e.addEventListener("contextmenu", function (e) {
    e.preventDefault()
    console.log(e.target)
    active = e.target
    d.querySelectorAll("#labyrinthGrid td").forEach(el => el.classList.remove("marked"))
    e.target.classList.toggle("marked")
}))

d.querySelectorAll("#labyrinthGrid td").forEach(e => e.addEventListener("click", function (e) {
    console.log(e.target)
    active = e.target
    d.querySelectorAll("#labyrinthGrid td").forEach(el => el.classList.remove("active"))
    e.target.classList.toggle("active")
}))

var active;
const table = d.getElementById("labyrinthGrid")
d.onkeydown = checkKey

function checkKey(e) {
    e = e || window.event;
    if (e.keyCode == '38') {
        // up arrow
        var idx = active.cellIndex;
        var nextrow = active.parentElement.previousElementSibling;
        var sibling
        if (nextrow != null) {
            sibling = nextrow.cells[idx];
        } else if (nextrow == null) {
            nextrow = table.rows[table.rows.length - 1]
            sibling = nextrow.cells[idx]
        }
        move(sibling)
    } else if (e.keyCode == '40') {
        // down arrow
        var idx = active.cellIndex;
        var nextrow = active.parentElement.nextElementSibling;
        var sibling
        if (nextrow != null) {
            sibling = nextrow.cells[idx];
        } else if (nextrow == null) {
            nextrow = table.rows[0]
            sibling = nextrow.cells[idx]
        }
        move(sibling);
    } else if (e.keyCode == '37') {
        // left arrow
        var idx = active.parentElement.rowIndex;
        var nextcell = active.previousElementSibling;
        var sibling
        if (nextcell != null) {
            sibling = active.previousElementSibling;
        } else if (nextcell == null) {
            nextcell = table.rows[idx]
            sibling = nextcell.cells[nextcell.cells.length - 1]
        }
        move(sibling);
    } else if (e.keyCode == '39') {
        // right arrow
        var idx = active.parentElement.rowIndex;
        var nextcell = active.nextElementSibling;
        var sibling
        if (nextcell != null) {
            sibling = active.nextElementSibling;
        } else if (nextcell == null) {
            nextcell = table.rows[idx]
            sibling = nextcell.cells[0]
        }
        move(sibling);
    } else if ([13, 32].includes(e.keyCode)) {
        console.log("enter or leer");
        markCell(active)
    }
}
