![JImageStreamToolkit Logo](https://github.com/kmisztal/JImageStreamToolkit/blob/master/JImageStreamToolkit_logo.png)

JImageStreamToolkit is a framework to quickly process Image files.

* **Open Source:**
JImageStreamToolkit is an Open-Source project allowing anyone to contribute.

* **Flexible:**
We offer some built-in plugins to process Images but it's very easy to develop your own plugin!

* **Easy to use:**
Framework is created with the aim of being easy to use. Thanks to extensive documentation and sample use examples you will process your images in less than 5 minutes!

## Contribution
* Create branch describing what are you working on. For example Executor, Loaders, Plugins
* Commit your changes often [Why?](http://www.databasically.com/2011/03/14/git-commit-early-commit-often/)
* When you finish small part of your module create pull request. [How?](https://help.github.com/articles/creating-a-pull-request/)
* Pull request has to be small <200 changes. If you will make big pull request be aware that it might take very long time to merge it and you will be slowed down
. [Why?](http://robertheaton.com/2015/10/26/why-and-how-to-make-smaller-pull-requests/)
* Schema for code pushing is:
Your branch -> Pull Request -> dev -> Pull Request -> stage -> Pull Request -> master
[Link](https://help.github.com/articles/about-pull-request-reviews/)
* Create pull request's to dev very often and merge all dev changes as soon as possible. Thanks to this everyone will have access to last project stage. For example team working on plugin has finished first example plugin and team for executor is waiting to inject real plugins into their code. If we will push working code ( but whole module don't have to be completly finished) to dev branch we all will have up to date project status. Only rule is to push working code that will not crush whole project.



## Code Style

JImageStreamToolkit follows standard Java coding conventions with the additional rules described below.

* Follow the commonly accepted good practices e.g. 
[The Boy Scout Rule](http://programmer.97things.oreilly.com/wiki/index.php/The_Boy_Scout_Rule), 
[DRY](http://programmer.97things.oreilly.com/wiki/index.php/Don%27t_Repeat_Yourself) ,
[SOLID](https://scotch.io/bar-talk/s-o-l-i-d-the-first-five-principles-of-object-oriented-design) 
etc.
* Write code (and comments) in English.
* Do not commit commented out code.
* Do not ignore exceptions. You must handle every exception in your code in a principled way. The specific handling varies depending on the case. Do not write e.g. `catch (NumberFormatException e) { }`
* Do not catch or throw generic exception. Catch each exception separately as separate catch blocks after a single try.
* Add message to each exception with explanation of cause with all additional data. For example when the file was not found add path to this file in exception message.
* Use TODO comments (`//TODO exaplanation`) for code that is temporary, a short-term solution, or good-enough but not perfect.
* Do not leave empty blocks of code. If you need to do this, e.g. private constructor in utility classes, leave a comment.
* Pay attention to the correct variable, methods etc. naming. The name must comply with the conventions used in Java. Additionally name cannot be a single letter except very short methods or the counters in simple loops.
* Committed code must compiles and passes all the tests.
* Write thread-safe code. This will allow to run project on multiple machines in the future.
* Unit tests should cover the maximum possible amount of code. Remember about edge-case tests.
* Do not use `null`, optional objects keep in `Optional` wrapper.
* Each interface and abstract class needs [Javadoc](http://www.oracle.com/technetwork/articles/java/index-137868.html).
* Do not use code marked by `@Deprecated` or located in `legacy` package
