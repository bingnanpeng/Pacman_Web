'use strict';

let bg; // bg canvas.
let app;
let energizer_timer = true;
let counter = 0;

const TIMEOUT = 80;
const REFRESH_INTERVAL = 4;

const COLOR = {
    "shadow": '#F00',
    "bashful": '#0CF',
    "pokey": '#F83',
    "speedy": '#F9C',
    "wall": '#17F',
    "dot": '#FD2',
    "gate": '#b0f',
    "energizer": '#FB5',
    "background": '#222'
};

const UNIT_WIDTH = 20;
const UNIT_HEIGHT = 20;
const BORDER = 10;
const SMALL_RADIUS = 4;
const BIG_RADIUS = 8;

let intervalId = null;

let level1 = {
    "gameover": false,
    "level": 0,
    "frightenedTimer": 4,
    "staticMatrix": [
        [9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9],
        [9, 1, 1, 3, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 1, 1, 3, 9, 9, 1, 1, 1, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 6, 6, 6, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 6, 6, 4, 9, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9],
        [9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 2, 6, 6, 4, 9, 1, 9, 9, 9, 9, 9, 5, 9, 9, 9, 9, 9, 1, 9],
        [9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 2, 6, 6, 4, 9, 1, 9, 9, 9, 9, 9, 7, 9, 9, 9, 9, 9, 1, 9],
        [9, 1, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 6, 6, 4, 9, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 6, 6, 6, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 9, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9],
        [9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 9],
        [9, 1, 9, 9, 9, 1, 9, 9, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9, 9, 1, 9],
        [9, 1, 1, 3, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 1, 1, 1, 3, 9, 9, 1, 1, 1, 1, 9],
        [9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9]
    ],
    "players": [{
        "id": 0,
        "life": 3,
        "ghostEatenNum": 0,
        "skillStrategy": [
            {
                "name": "accelerate",
                "cd": 0,
                "lasting": 0,
                "maxCd": 500,
                "maxLasting": 50,
                "cost": 0
            },
            {
                "name": "teleport",
                "cd": 0, "lasting": 0,
                "maxCd": 500,
                "maxLasting": 0,
                "cost": 0
            }
        ],
        "speed": 5,
        "direction": "LEFT",
        "location": {"x": 280, "y": 480},
        "invincible": false,
        "willTurn": false,
        "updateStrategy": {"type": "pacman"},
        "type": "player",
        "score": 0
    }, {
        "id": 1,
        "life": 3,
        "ghostEatenNum": 0,
        "skillStrategy": [{
            "name": "teleport",
            "cd": 0,
            "lasting": 0,
            "maxCd": 500,
            "maxLasting": 0,
            "cost": 0
        }, {"name": "invincible", "cd": 0, "lasting": 0, "maxCd": 500, "maxLasting": 50, "cost": 0}],
        "speed": 5,
        "direction": "LEFT",
        "location": {"x": 300, "y": 480},
        "invincible": false,
        "willTurn": false,
        "updateStrategy": {"type": "pacman"},
        "type": "player",
        "score": 0
    }],
    "ghosts": [{
        "duration": 0,
        "isEaten": false,
        "isHome": false,
        "viewRange": 10000000,
        "chase": {"type": "bashful"},
        "retreat": {"type": "retreat"},
        "wander": {"type": "wander"},
        "revive": {"type": "revive"},
        "speed": 10,
        "direction": "LEFT",
        "location": {"x": 260, "y": 320},
        "invincible": true,
        "willTurn": false,
        "updateStrategy": {"type": "wander"},
        "type": "ghost",
        "score": 0
    }, {
        "duration": 0,
        "isEaten": false,
        "isHome": false,
        "viewRange": 10000000,
        "chase": {"type": "pokey"},
        "retreat": {"type": "retreat"},
        "wander": {"type": "wander"},
        "revive": {"type": "revive"},
        "speed": 10,
        "direction": "LEFT",
        "location": {"x": 280, "y": 320},
        "invincible": true,
        "willTurn": false,
        "updateStrategy": {"type": "wander"},
        "type": "ghost",
        "score": 0
    }, {
        "duration": 0,
        "isEaten": false,
        "isHome": false,
        "viewRange": 10000000,
        "chase": {"type": "shadow"},
        "retreat": {"type": "retreat"},
        "wander": {"type": "wander"},
        "revive": {"type": "revive"},
        "speed": 10,
        "direction": "LEFT",
        "location": {"x": 300, "y": 320},
        "invincible": true,
        "willTurn": false,
        "updateStrategy": {"type": "wander"},
        "type": "ghost",
        "score": 0
    }, {
        "duration": 0,
        "isEaten": false,
        "isHome": false,
        "viewRange": 10000000,
        "chase": {"type": "speedy"},
        "retreat": {"type": "retreat"},
        "wander": {"type": "wander"},
        "revive": {"type": "revive"},
        "speed": 10,
        "direction": "LEFT",
        "location": {"x": 320, "y": 320},
        "invincible": true,
        "willTurn": false,
        "updateStrategy": {"type": "wander"},
        "type": "ghost",
        "score": 0
    }]
};

function createApp(canvas) {
    let c = canvas.getContext("2d");
    let dims = {height: canvas.height, width: canvas.width};

    let drawCircle = function (x, y, radius, color) {
        c.fillStyle = color;
        c.beginPath();
        c.arc(x + UNIT_WIDTH / 2, y + UNIT_HEIGHT / 2, radius, 0, 2 * Math.PI);
        c.fill();
    };

    let drawImage = function (id, x, y, ratio) {
        let im = document.getElementById(id);

        c.save();
        c.translate(x - UNIT_WIDTH * (ratio - 1) / 2, y - UNIT_HEIGHT * (ratio - 1) / 2);
        c.drawImage(im, 0, 0, UNIT_WIDTH * ratio, UNIT_HEIGHT * ratio);
        c.restore();

        // console.log(im);
    };

    let showGameOver = function () {
        c.fillStyle = COLOR.background;
        c.fillRect(0, 0, 800, 640);
        drawImage("gameover", 385, 320, 20);
        app.pause = false;
    }

    let drawGhost = function (id, x, y) {
        // alert(src + "," + x + "," + y + "," +width + "," + height +"," +angle);
        let im = document.getElementById(id);

        c.save();
        c.translate(x - UNIT_WIDTH / 2, y - UNIT_WIDTH / 2);
        // console.log(id);
        c.drawImage(im, 0, 0, UNIT_WIDTH, UNIT_HEIGHT);
        c.restore();

        // console.log(im);
    };


    let drawPlayer = function (id, x, y) {
        let im = document.getElementById(id);

        c.save();
        c.translate(x - UNIT_WIDTH * 0.6, y - UNIT_HEIGHT * 0.6);
        c.drawImage(im, 0, 0, UNIT_WIDTH * 1.2, UNIT_HEIGHT * 1.2);
        c.restore();
    };

    let drawScoreAndLevel = function (level, score, x, y) {
        c.font = "16px Arial";
        c.fillText("LEVEL :     " + level, x, y);
        c.fillText("SCORE :    " + score, x, y + 35);
    }

    let drawTitle = function (id, x, y) {
        let im = document.getElementById(id);
        c.save();
        c.translate(x, y);
        c.drawImage(im, 0, 0, 200, 140);
        c.restore();
    }

    let drawPlayerInfo = function (playerId, life, skills, x, y) {
        c.font = "16px Arial";
        if (playerId == 0) {
            c.fillText("PLAYER # " + (playerId + 1) + "-(WASD)-(123)", x, y);
        } else {
            c.fillText("PLAYER # " + (playerId + 1) + "-(↑↓←→)-(<>?)", x, y);
        }
        // alert(life);

        // drawImage('pacman'+playerId+"_right", x, y);
        c.font = "16px Arial";
        c.fillText("LIFE   : ", x, y + 35);
        for (let i = 0; i < life; i++) {
            drawImage('pacman' + playerId + "_right", x + 30 * i + 65, y + 18, 1.2);
        }

        c.fillText("SKILL : ", x, y + 70);
        for (let i = 0; i < skills.length; i++) {
            if (skills[i].lasting > 0 && energizer_timer) {
                continue;
            }
            drawImage(skills[i].name, x + 65 + 30 * i, y + 58, 1.2);
        }

    }


    let draw = {};

    draw['dot'] = function (x, y) {
        drawCircle(x, y, SMALL_RADIUS, COLOR.dot);
    };

    draw['energizer'] = function (x, y) {
        if (energizer_timer) {
            drawCircle(x, y, BIG_RADIUS, COLOR.energizer);
        }
    };

    draw['triangle'] = function (x, y) {
        c.fillStyle = COLOR.wall;
        c.fillRect(x, y, UNIT_WIDTH, UNIT_HEIGHT);
    };

    draw['gate'] = function (x, y) {
        c.fillStyle = COLOR.gate;
        c.fillRect(x, y + UNIT_HEIGHT / 2, UNIT_WIDTH, UNIT_HEIGHT / 2);
    };

    draw['fruit'] = function (x, y) {
        drawImage('fruit', x, y, 1);
    };

    // 10/25 draw wall special:
    draw['horizonalWall'] = function (x, y) {
        drawImage('horizonalWall', x, y, 1);
    };

    draw['verticalWall'] = function (x, y) {
        drawImage('verticalWall', x, y, 1);
    };

    draw['upperLeftWall'] = function (x, y) {
        drawImage('upperLeftWall', x, y, 1);
    };

    draw['upperRightWall'] = function (x, y) {
        drawImage('upperRightWall', x, y, 1);
    };

    draw['lowerLeftWall'] = function (x, y) {
        drawImage('lowerLeftWall', x, y, 1);
    };

    draw['lowerRightWall'] = function (x, y) {
        drawImage('lowerRightWall', x, y, 1);
    };

    let clear = function () {
        c.clearRect(0, 0, canvas.width, canvas.height);
        c.fillStyle = COLOR.background;
        c.fillRect(0, 0, canvas.width, canvas.height);
    };

    let convert = function (i, j) {
        return {
            x: i * UNIT_WIDTH + BORDER,
            y: j * UNIT_HEIGHT + BORDER
        }
    };

    //let ghostLoc = [];
    let drawItem = function (item, x, y) {
        switch (item) {
            case 1:
                draw['dot'](x, y);
                break;
            case 2:
                draw['gate'](x, y);
                break;
            case 3:
                draw['energizer'](x, y);
                break;
            case 4:
                // ghost initial loc
                break;
            case 5:
                // player 1 initial loc
                break;
            case 6: // ghost home
                break;
            case 7:
                // player 2 initial loc
                break;
            case 8:
                draw['fruit'](x, y);
                break;
            //case 9:           10/25: build a special draw wall function
            //    draw['triangle'](x, y);
            //    break;
            default:
        }
    };

    let drawWall = function (map) {
        let row = map.length;
        let col = map[0].length;
        for (let i = 0; i < row; i++) {
            for (let j = 0; j < col; j++) {
                if (map[i][j] === 9) {
                    let left = false;
                    let right = false;
                    let up = false;
                    let down = false;
                    let upLeft = false;
                    let upRight = false;
                    let downLeft = false;
                    let downRight = false;
                    // map (left,right,up,down) = canvas (up,down,left,right)
                    if (i === 0 || map[i - 1][j] !== 9) left = true;
                    if (i === row - 1 || map[i + 1][j] !== 9) right = true;
                    if (j === 0 || map[i][j - 1] !== 9) up = true;
                    if (j === col - 1 || map[i][j + 1] !== 9) down = true;

                    if (i === 0 || j === 0 || map[i - 1][j - 1] !== 9) upLeft = true;
                    if (i === 0 || j === col - 1 || map[i - 1][j + 1] !== 9) downLeft = true;
                    if (i === row - 1 || j === 0 || map[i + 1][j - 1] !== 9) upRight = true;
                    if (i === row - 1 || j === col - 1 || map[i + 1][j + 1] !== 9) downRight = true;
                    let {x, y} = convert(i, j);
                    if (up && left) {
                        draw['upperLeftWall'](x, y);
                    } else if (up && right) {
                        draw['upperRightWall'](x, y);
                    } else if (down && left) {
                        draw['lowerLeftWall'](x, y);
                    } else if (down && right) {
                        draw['lowerRightWall'](x, y);
                    } else if (up || down) {
                        draw['horizonalWall'](x, y);
                    } else if (left || right) {
                        draw['verticalWall'](x, y);
                    } else if (upLeft) {
                        draw['lowerRightWall'](x, y);
                    } else if (upRight) {
                        draw['lowerLeftWall'](x, y);
                    } else if (downLeft) {
                        draw['upperRightWall'](x, y);
                    } else if (downRight) {
                        draw['upperLeftWall'](x, y);
                    }
                }
            }
        }
    };

    let loadGame = function (level, draw_background) {
        console.log(level);

        if (level.gameover) {
            showGameOver();
            return;
        }

        let map = level.staticMatrix;
        let row = map.length;
        let col = map[0].length;
        for (let i = 0; i < row; i++) {
            for (let j = 0; j < col; j++) {
                let {x, y} = convert(i, j);
                drawItem(map[i][j], x, y);
            }
        }

        // draw wall
        if (draw_background) {
            drawWall(map);
        }

        // draw players
        let players = level.players;
        let score = 0;
        players.forEach(function (player) {
            let id = player.id;
            let location = player.location;
            let direction = player.direction;
            let cur_skill = '';
            player.skillStrategy.forEach(function (skill) {
                if (skill.lasting > 0) {
                    cur_skill = skill.name;
                }
            });

            score += player.score;

            if (cur_skill === '') {
                drawPlayer('pacman' + id + '_' + direction.toLowerCase(), location.x, location.y);
            } else {
                drawPlayer(cur_skill + '_pacman' + id + '_' + direction.toLowerCase(), location.x, location.y);
            }
        });

        // draw ghosts
        let ghosts = level.ghosts;
        ghosts.forEach(function (ghost) {
            let name = ghost.updateStrategy.type;
            if ('retreat' === name && level.frightenedTimer > 0 && level.frightenedTimer < 20 && energizer_timer) {
                name = name + '_blink';
                console.log(name);
            }
            if (!['revive', 'retreat', 'retreat_blink'].includes(name)) {
                name = ghost.chase.type;
            }
            let location = ghost.location;
            drawGhost(name, location.x, location.y);
        });
        drawTitle("title", 580, 10);
        drawScoreAndLevel(level.level, score.toString(), 590, 200);
        for (let i = 0; i < players.length; i++) {
            drawPlayerInfo(players[i].id, players[i].life, players[i].skillStrategy, 590, 300 + i * 150);
        }

        // showGameOver();

    };

    return {
        clear: clear,
        loadGame: loadGame,
        dims: dims,
        pause: false
    }
}

function start() {
    if (intervalId == null) {
        intervalId = setInterval(update_pacman, TIMEOUT);
    }
}

window.onload = function () {
    // Get the modal
    let modal = $("#myModal");
    // modal.style.display = "none";

    // Get the <span> element that closes the modal
    let span = $("#btn-close");

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        // modal.style.aria-hidden = "false";
        app.pause = false;
        // $("myModal").showModal();
    };

    document.getElementById("submit-btn").onclick = function() {
        let level = 0;
        let mode = 1;
        level = parseInt(document.querySelector(".active").getAttribute('data-slide-to'));
        mode = parseInt($('input[name=optionsRadiosinline]:checked').val());
        $("#close-btn").click();
        loadLevel(level, mode);
        app.pause = false;
    };


    bg = createApp(document.querySelector("canvas"));
    app = createApp(document.querySelector("canvas"));

    // TODO: use following later
    // clear();
    app.clear();

    // canvasDims();

    load_pacman();

    app.pause = true;
    modal.modal("show");

    window.onkeydown = function () {
        switch (event.keyCode) {
            case 32: // SPACE
                console.log("SPACE");
                if (!app.pause) {
                    app.pause = true;
                    modal.modal("show");
                } else {
                    app.pause = false;
                }
                break;
            case 37: // LEFT
                console.log("LEFT");
                cmd_pacman(1, "LEFT");
                start();
                break;
            case 38: // UP
                console.log("UP");
                cmd_pacman(1, "UP");
                start();
                break;
            case 39: // RIGHT
                console.log("RIGHT");
                cmd_pacman(1, "RIGHT");
                start();
                break;
            case 40: // DOWN
                console.log("DOWN");
                cmd_pacman(1, "DOWN");
                start();
                break;
            case 49: // 1
                console.log("1");
                cmd_pacman(0, "accelerate");
                break;
            case 50: // 2
                console.log("2");
                cmd_pacman(0, "invincible");
                break;
            case 51: // 3
                console.log("3");
                cmd_pacman(0, "teleport");
                break;
            case 65: // A
                console.log("A");
                cmd_pacman(0, "LEFT");
                start();
                break;
            case 87: // W
                console.log("W");
                cmd_pacman(0, "UP");
                start();
                break;
            case 68: // D
                console.log("D");
                cmd_pacman(0, "RIGHT");
                start();
                break;
            case 83: // S
                console.log("S");
                cmd_pacman(0, "DOWN");
                start();
                break;
            case 188: // ,
                console.log(",");
                cmd_pacman(1, "accelerate");
                break;
            case 190: // .
                console.log(".");
                cmd_pacman(1, "invincible");
                break;
            case 191: // 3
                console.log("/");
                cmd_pacman(1, "teleport");
                break;
            default:

        }
    }

};

function load_pacman() {
    $.post("/load", {level: 0, mode: 2}, function (data) {
        app.clear();
        // load canvas
        console.log(data);

        app.loadGame(data, true);
    }, "json");
}

function update_pacman() {
    if (!app.pause) {
        $.post("/update", function (data) {
            app.clear();
            // update canvas

            counter++;
            counter %= REFRESH_INTERVAL;

            if (counter % REFRESH_INTERVAL == 0) {
                energizer_timer = !energizer_timer;
            }
            console.log(data);

            app.loadGame(data, true);
        }, "json");
    }
}

// function loadLevel() {
//     $("#")
// }

function cmd_pacman(id, key) {
    $.post("/key", {id: id, key: key}, function (data) {
        // update canvas
    }, "json");
}

function clear() {
    $.post("/reset", function (data) {

    }, "json");
    if (intervalId !== null) {
        clearInterval(intervalId);
        intervalId = null;
    }
    app.clear();
}

function loadLevel(level, mode) {
    $.post("/load", {level: level, mode: mode}, function (data) {
        app.clear();
        // load canvas
        console.log(data);

        app.loadGame(data, true);
    }, "json");
}
