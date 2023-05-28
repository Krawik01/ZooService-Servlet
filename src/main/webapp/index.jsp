<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Zoo Page</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>

<h1 class = "Title"><%= "ZOO FINDER" %></h1>

<div class = "FindAnimalText" > Find Animal</div>

<form action="/TpoWebApp_war_exploded/api/animals" method="get">
    <label class ="NameText" for="Name">Name: </label> <br/>
    <input class="NameBox" type="text" id="Name" name="Name"/> <br/>

    <label class ="SpeciesText" for="Species">Species: </label> <br/>
    <input class="SpeciesBox" type="text" id="Species" name="Species"/> <br/>

    <label class="GenderText" for="Gender"> Gender: </label> <br/>
    <select class="GenderSelect" name="Gender" id="Gender">
     <option value="ALl"> All </option>
     <option value="Male"> Male </option>
     <option value="Female"> Female </option>
    </select>
        <br/>

    <button class="SearchButton" type="Search" id="SearchButton">Search</button>
</form>

<div class = "FindCareTakerText" > Find care taker</div>

<form action="/TpoWebApp_war_exploded/api/caretakers" method="get">
    <label class="careNameText" for="careTakerName"> First Name: </label> <br/>
    <input class="careNameBox" type="text" id="careTakerName" name="careTakerName"/> <br/>
    <label class="careLastNameText" for="careTakerLastName"> Last Name: </label> <br/>
    <input class = "careLastNameBox" type="text" id="careTakerLastName" name="careTakerLastName"/> <br/>
    <label class ="carePhoneText" for="careTakerPhone"> Phone Number: </label> <br/>
    <input class ="carePhoneBox" type="text" id="careTakerPhone" name="careTakerPhone"/> <br/>

    <button class ="careSearchButton" type="Search" id="SearchButtonCareTaker">Search</button>
</form>

<br/>
</body>
</html>