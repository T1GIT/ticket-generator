const
    cover = document.getElementById("cover"),
    form = document.querySelector("form"),
    fileLoader = document.getElementById("fileLoader"),
    subjectSelector = document.getElementById("subjectSelector"),
    themeSelector = document.getElementById("themeSelector"),
    theoryCounter = document.getElementById("theoryCounter"),
    practiceCounter = document.getElementById("practiceCounter"),
    ticketCounter = document.getElementById("ticketCounter"),
    generateButton = document.getElementById("generateButton"),
    ticketSelector = document.getElementById("ticketSelector"),
    theoryTaskList = document.getElementById("theoryTaskList"),
    practiceTaskList = document.getElementById("practiceTaskList"),
    ticketDiv = document.getElementById("ticket"),
    loadOneButton = document.getElementById("loadOneButton"),
    loadAllButton = document.getElementById("loadAllButton"),
    title = document.querySelector("title"),
    h1 = document.querySelector("h1")


document.oldTitle = title.innerText
let timer

function easterEgg(show) {
    const period = 100
    const keyWord = "TRIIKSTERKA"
    let ready = false
    let newWord
    if (timer) clearInterval(timer)
    newWord = show ? keyWord : document.oldTitle;
    timer = setInterval(function () {
        if (title.innerText.length === 1 && !ready) {
            ready = true
            title.innerText = newWord[0]
        }
        else if (title.innerText === newWord) clearInterval(timer)
        else if (ready) title.innerText += newWord[title.innerText.length]
        else title.innerText = title.innerText.slice(0, title.innerText.length - 1)
    }, period)
}

function controlRange(counter) {
    counter.addEventListener("change", function () {
        try {
            let [v, max, min] = [parseInt(counter.value), parseInt(counter.max), parseInt(counter.min)]
            if (v > max) counter.value = max
            else if (v < min) counter.value = min
            else if (v === parseInt("")) counter.value = min
        } catch (e) {
            counter.value = parseInt(counter.min)
        }
    })
}

function insertLoaderAfter(element) {
    let loader = document.createElement("div")
    loader.classList.add("preloader")
    loader.innerHTML = "<div></div>".repeat(4)
    let br = document.createElement("br")
    element.after(br)
    br.after(loader)
}

function removeLoaderAfter(element) {
    let br = element.nextElementSibling
    let loader = br.nextElementSibling
    br.remove()
    loader.remove()
}

function uploadExcel() {
    let file = fileLoader.files[0]
    fileLoader.value = ""
    if (!file.name.endsWith(".xls") && !file.name.endsWith(".xlsx")) alert("Файл должен ялятся документом Excel")
    else if (file.size > 1024 ** 2) alert("Размер файла не может превышать 1 МБ")
    else {
        insertLoaderAfter(fileLoader)
        let formData = new FormData()
        formData.append("file", file)
        axios.post("/subject", formData, {
            headers: {'Content-Type': 'multipart/form-data'}
        }).then(() => {
            removeLoaderAfter(fileLoader)
            fillSubjectSelector()
            if (form.hidden) form.hidden = undefined
        })
    }
}

function fillSubjectSelector() {
    axios.get('/subject')
        .then(({data}) => {
            subjectSelector.length = 0
            for (let i = 0; i < data.length; i++) {
                let {id, name} = data[i]
                subjectSelector.add(new Option(name, id))
            }
            if (data.length > 0) {
                form.hidden = undefined
                changeSubject()
            }
            else form.hidden = true
        })
}

function changeSubject() {
    let subjectId = subjectSelector.value
    axios.get('/theme/subject', {
        params: {
            subjectId: subjectId
        }})
        .then(({data}) => {
            themeSelector.length = 0
            for (let i = 0; i < data.length; i++) {
                let {id, name} = data[i]
                themeSelector.add(new Option(name, id))
            }
            if (data.length > 0) changeTheme()
        })
}

function changeTheme() {
    let themeId = themeSelector.value
    axios.get("/ticket/theme", {
        params: {
            themeId: themeId
        }})
        .then(({data: {tickets, amount: {theory, practice}}}) => {
            ticketSelector.length = 0
            for (let i = 0; i < tickets.length; i++) {
                let {id, num} = tickets[i]
                ticketSelector.add(new Option(num, id))
            }
            if (tickets.length > 0) {
                loadAllButton.hidden = undefined
                ticketDiv.hidden = undefined
                showTicket()
            }
            else {
                ticketDiv.hidden = true
                loadAllButton.hidden = true
                theoryTaskList.innerHTML = ""
                practiceTaskList.innerHTML = ""
            }
            if (theoryCounter.value > theory) theoryCounter.value = theory
            if (practiceCounter.value > practice) practiceCounter.value = practice
            theoryCounter.max = theory
            practiceCounter.max = practice
            loadAllButton.innerText = `Скачать билеты по теме ${themeSelector[themeSelector.selectedIndex].text}`
        })
}

function fillTaskList(taskList, tasks) {
    taskList.innerHTML = ""
    for (let i = 0; i < tasks.length; i++) {
        let {text} = tasks[i]
        let li = document.createElement("li")
        li.innerText = text
        taskList.appendChild(li)
    }
}

function generate() {
    if (practiceCounter.value === "0" && theoryCounter.value === "0") {
        alert("Невозможно создать билет без вопросов")
    } else {
        insertLoaderAfter(generateButton)
        generateButton.disabled = true
        let themeId = themeSelector.value
        axios.get("/ticket/create", {
            params: {
                themeId: themeId,
                theoryAmount: theoryCounter.value,
                practiceAmount: practiceCounter.value,
                amount: ticketCounter.value
            }})
            .then(({data}) => {
                removeLoaderAfter(generateButton)
                generateButton.disabled = undefined
                ticketSelector.length = 0
                for (let i = 0; i < data.length; i++) {
                    let {id, num} = data[i]
                    ticketSelector.add(new Option(num, id))
                }
                loadAllButton.hidden = undefined
                ticketDiv.hidden = undefined
                ticketSelector.selectedIndex = 0
                showTicket()
            })
    }
}

function showTicket() {
    let ticketId = ticketSelector.value
    axios.get("/ticket", {
        params: {
            ticketId: ticketId
        }})
        .then(({data}) => {
            let {theories, practices} = data
            fillTaskList(theoryTaskList, theories)
            fillTaskList(practiceTaskList, practices)
            loadOneButton.innerText = `Скачать билет №${ticketSelector[ticketSelector.selectedIndex].text}`
        })
}

function loadOneTicket() {
    let ticketId = ticketSelector.value
    axios.get("/ticket/doc", {
        responseType: "blob",
        params: {
            ticketId: ticketId
        }})
        .then(({data}) => {
            const link = document.createElement('a')
            let subject = subjectSelector[subjectSelector.selectedIndex].text
            let theme = themeSelector[themeSelector.selectedIndex].text
            let ticket = ticketSelector[ticketSelector.selectedIndex].text
            link.download =`${subject} ${theme} Билет №${ticket}.docx`
            link.href = window.URL.createObjectURL(new Blob([data]))
            link.click();
        })
}

function loadAllTickets() {
    const loaderLimit = 100
    let themeId = themeSelector.value
    if (ticketSelector.length > loaderLimit) insertLoaderAfter(loadAllButton)
    axios.get("/ticket/doc/theme", {
        responseType: "blob",
        params: {
            themeId: themeId
        }})
        .then(({data}) => {
            if (ticketSelector.length > loaderLimit) removeLoaderAfter(loadAllButton)
            const link = document.createElement('a')
            let subject = subjectSelector[subjectSelector.selectedIndex].text
            let theme = themeSelector[themeSelector.selectedIndex].text
            link.download = `${subject} ${theme}.docx`
            link.href = window.URL.createObjectURL(new Blob([data]))
            link.click();
        })
}

function dragAndDropConfiguring() {
    let zone = fileLoader.previousElementSibling
    zone.addEventListener("dragenter", function (event) {
        zone.classList.add("drag")
    })
    zone.addEventListener("dragleave", function (event) {
        zone.classList.remove("drag")
    })
    zone.addEventListener("dragover", function (event) {
        event.preventDefault()
        event.stopPropagation()
    })
    zone.addEventListener("drop", function (event) {
        event.preventDefault();
        zone.classList.remove("drag")
        fileLoader.files = event.dataTransfer.files;
        uploadExcel()
    })
}


h1.addEventListener("mousedown", () => easterEgg(true))
h1.addEventListener("mouseup", () => easterEgg(false))

fileLoader.addEventListener("change", uploadExcel)
subjectSelector.addEventListener("change", changeSubject)
themeSelector.addEventListener("change", changeTheme)
controlRange(theoryCounter)
controlRange(practiceCounter)
controlRange(ticketCounter)
generateButton.addEventListener("click", generate)
ticketSelector.addEventListener("change", showTicket)
loadOneButton.addEventListener("click", loadOneTicket)
loadAllButton.addEventListener("click", loadAllTickets)
dragAndDropConfiguring()


setTimeout(() => cover.classList.remove("hidden"), 200)
fillSubjectSelector()
