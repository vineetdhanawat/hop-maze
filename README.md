# HopMaze
Import the android-app  project in eclipse. You are good to go!

Proof of Concept only

The game is based on real life maze concept. Gameplay requires user to hop around a plane arena, replicating motion as a pattern in game. Presented during the Droidcon'11 Hacknight.
* Final (Informal) Presentation Video : http://youtu.be/Qa6tqdbhB8w
* Details of other projects: https://droidcon.in/2011/hacknight-videos
* Source Code : https://github.com/vineetdhanawat/hop-maze

## How To Play
- **Display**: Screen displays a 3x3 grid, which is a small part of actual maze 8x8 grid.
- **Position**: Keep your device horizontal at all times, perfectly aligned to the ground.
- **Starting**: Initial direction where you start the game is always recorded, rest of directions are relative.
- **Move**: Jump a step ahead, direction in the device will be simulated to the pointing arrow in game.
- **Turn**: Just move 90 degrees clockwise or anticlockwise to rotate the direction.

You cannot jump back. You need to turn 180 degrees and jump again.
Keep jumping and moving until you reach the end of maze. You typically start from bottom right corner of maze and you need to reach top-left to win the game.

Game may contains bugs as this is only a POC. Tested and works on Nexus 5. Failed on Nexus 7.

## Privacy Policy
App store requires privacy policy. https://github.com/vineetdhanawat/hop-maze/wiki/Privacy-Policy

## License

MIT: http://vineetdhanawat.mit-license.org/