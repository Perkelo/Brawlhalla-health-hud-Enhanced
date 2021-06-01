# Brawlhalla Health HUD Enhanced

#### "Brogle🥦"  -BirdsCanFlyWhyCantI, 2021


#### Improvement on [Brawlhalla Health HUD V2 by BFCE](https://github.com/BFCE/Brawlhalla-health-hud-V2)

This is an open-source Java application that overlays the health of the players onto your screen. Since dll injection in Java isn't really a thing, it achieves this by making itself an "always on top" window, and tricking windows into thinking a fullscreen application is on the top.

***The tool only works when Brawlhalla is in fullscreen, for now.***

## Default mode

![picture](img/1s.png)

![picture](img/2s.png)

## Smash bros inspired UI mode

![picture](img/s1s.png)

![picture](img/s2s.png)

## How to use

1. Download Java
2. Put the jar in a folder somewhere (this is where your config will be saved)
3. Open it
4. Select mode
5. Press <kbd>F9</kbd> to close it


## Editing the UI

#### Default mode:
If you press <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>Alt</kbd> in default mode, the overlay will be moved to your current mouse position.

#### Smash mode:
You can change position, transparency, and size of the overlay using some shortcuts:

* <kbd>F2</kbd> + <kbd>Left Arrow</kbd> / <kbd>Right Arrow</kbd> - Increases / decreases the player icon size
* <kbd>F3</kbd> + <kbd>Left Arrow</kbd> / <kbd>Right Arrow</kbd> - Increases / decreases the font size for the damage display
* <kbd>F1</kbd> + <kbd>Left Arrow</kbd> / <kbd>Right Arrow</kbd> - Increases / decreases both icon and font size
* <kbd>F4</kbd> + <kbd>Left Arrow</kbd> / <kbd>Right Arrow</kbd> - Increases / decreases the overlay's transparency
* <kbd>F5</kbd> + <kbd>Left Arrow</kbd> / <kbd>Right Arrow</kbd> / <kbd>Up Arrow</kbd> / <kbd>Down Arrow</kbd> - Moves the overlay windows

After you made your changes, press <kbd>F8</kbd> to save your preferences. Next time you'll open the tool, the overlay will have the same configuration.

If you have problems with the tool not opening anymore after saving your settings, try deleting/moving your `ones.json` and `twos.json` files and reopening the tool.


### New features might be added later, and suggestions are highly welcome! Write me at Perkelo#5314 on Discord if you have ideas/bugs/just want to talk!


# Contributions

[BFCE](https://github.com/BFCE/) for [Brawlhalla Health HUD V2](https://github.com/BFCE/Brawlhalla-health-hud-V2) (The original tool)

[kristian](https://github.com/kristian) for [system-hook](https://github.com/kristian/system-hook)

[Sasjo](https://github.com/sasjo/) for [multiline](https://github.com/sasjo/multiline)
