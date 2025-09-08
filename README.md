# Programs as Data
Source code repository for code handed out during course.

## A what?

In this course you will write small
[lexers](https://en.wikipedia.org/wiki/Lexical_analysis) and
[parsers](https://en.wikipedia.org/wiki/Parsing#Parser) using
respectively [FsLex](https://fsprojects.github.io/FsLexYacc/) and
[FsYacc](https://fsprojects.github.io/FsLexYacc/), and we have decided
to keep it as minimal as possible. Instead of a full-blown Visual
Studio project with excessively many tools and nuget packages, we have
opted for a more minimal approach: you basically download and install
the tools locally, and run them in your command line (typically
Terminal.app on macOS, terminal on Linux and CMD/Powershell on
Windows). If you are unfamiliar with running applications or scripts
in your command line, or if all of this sounds like alien talk, please
read the [following
guide](https://www.theodinproject.com/paths/foundations/courses/foundations/lessons/command-line-basics-web-development-101),
and do the exercises too. The guide is mostly relevant for Mac/Linux,
but you can surely find stuff for Windows Powershell/CMD, too. The
command line is a dear friend that you will use a lot in your
professional life as well.

### FsLexYacc

The lexer genrator (`fslex`) and parser generator (`fsyacc`) are two
commandline tools used to generate a lexer and parser givnen a lexer--
and parser-- specification respectively.

The lexer and parser generators generate F# code that will either lex
or parse a given input string. The generated code depends on a runtime
library `FsLexYacc.Runtime.dll` that is packaged together with the two
generators, `fslex` and `fsyacc`.

All examples in this course is prepared to use the `dotnet`tool as it
seems the easiest way to support Windows, Mac and Linux. However, you
can also use the F# compiler directly from the commandline, e.g.,
`fsc.exe` or `fsharpc` depending on whether you are on Windows or
Mac/Linux. On Mac/Linux this approach requires `Mono`, such that you
can run the Windows executables directly.

### Installation

#### F# Compiler and Interactive

Assumption is that the F# compiler and interactive have been installed
and that you are able to start F# interactive and the compiler from
the commandline. Atleast one the options to start interactive and
compile should work for you.

Options to start F# interactive 
- `dotnet fsi` 
- `fsharpi`or `fsi` 

Options to compile F#
- `dotnet build xxxx.fsproj` where xxxx.fsproj is an appropriate project file.
- `fsharpc` or `fsc` 

#### Install FsLexYacc

You need to install version 11.3.0 of FsLexYacc. You can do that using
a nuget package manager or simply grap the two folders
`FsLexYacc.11.3.0` and `FsLexYacc.Runtime.11.3.0` from the `fsharp`
folder in our repo.

You copy the two folders to a place that you can refer to from all
your assignments in this course. For instance, you can copy the folder
`fsharp` to the root of your home directory. On Mac that would be
something like

```{zsh}
% ll ~/fsharp

... FsLexYacc.11.3.0
... FsLexYacc.Runtime.11.3.0
```

We then need easy access to the three files `fslex.dll`,
`fsyacc.dll`and the accompanying runtime
`FsLexYacc.Runtime.dll`. Symbolic links is one option and
alternatively copy the files from the respective sub-folders.

To make the symbolic links on Mac and Linux, starting in the `fsharp` folder:

```{zsh}
% ln -s FsLexYacc.Runtime.11.3.0/lib/netstandard2.0/FsLexYacc.Runtime.dll .
% ln -s FsLexYacc.11.3.0/build/fslex/net6.0/fslex.dll .
% ln -s FsLexYacc.11.3.0/build/fsyacc/net6.0/fsyacc.dll .
```

You then get the additional symbolic links in the `fsharp` folder:

```{zsh}
... FsLexYacc.Runtime.dll -> FsLexYacc.Runtime.11.3.0/lib/netstandard2.0/FsLexYacc.Runtime.dll
... fslex.dll -> FsLexYacc.11.3.0/build/fslex/net6.0/fslex.dll
... fsyacc.dll -> FsLexYacc.11.3.0/build/fsyacc/net6.0/fsyacc.dll
```

You should now be able to run the lexer and parser generater from the
commandline. Verify you can do below from your `fsharp` folder.

```{zsh}
% dotnet fslex.dll
FSLEX: error FSL000: no input given ...

% dotnet fsyacc.dll                     
FSYACC: error FSY000: no input given ...
```

This verifies that you can run the two generators. Of course, with no
lexer-- and parser-- specification they return with an error message.

You can now place yourself in any directory and start the generators
providing the full path to the `fsharp` folder. For instance

```{zsh}
% dotnet ~/fsharp/fslex.dll 
FSLEX: error FSL000: no input given%        
```

A simple optimization to be able to run `fslex` and `fsyacc` from
anywhere is to make an alias or similar. E.g., on Linux and Mac you
can create below aliases in your `.zshrc` or `.bashrc` files.

```{zsh}
alias fslex="dotnet <path_to_fsharp_folder>/fsharp/fslex.dll"
alias fsyacc="dotnet <path_to_fsharp_folder>/fsharp/fsyacc.dll"
```

You should then be able to write `fslex`and `fsyacc` from any folder.

Always remember to start a new terminal or source your `.zshrc` or
`.bashrc` files before your configuration changes will take affect.

For Windows you can obtain a similar setup with e.g., `.bat` or `.ps`
scripts that works from the CMD or PowerShell commandline.

#### Mac/Linux using Mono

If you compile using `fsc` or `fsharpc` you will get Windoes `.exe`
files. These can only be executed if you install Mono on Linux and
Mac. With Mono installed you can write

`mono my_executable.exe`

You are welcome to do this, but it is not necessary for this
course. We can use the `dotnet` tool.

**Overview of tool usage**

Each project in this course has a `README` file on how to compile and
run the examples.

Below shows the general use of the tools in this course:

| Action | Command |
| -- | --- |
| Generate lexer | `fslex --unicode <PATH_TO_FILE>.fsl` |
| Generate parser | `fsyacc --module <MODULE_NAME> <PATH_TO_FILE>.fsy` |
| Start interactive | `dotnet fsi -r <PATH_TO_FsLexYacc.Runtime.dll> <PATH_TO_FILE>.fs*` |
| Compile | `dotnet build xx.fsproj` |

*The xx.fsproj project files contains references to the FsLexYacc
 runtime to include and what files to compile. You need to update the
 project files to reflect your installation. They assume folder is in
 `~/fsharp/` and should be updated to `C:\fsharp` or whereever you
 placed the `fsharp` folder.*

## Known issues

* If you have issues with no syntax check (Intellisense) or type
  annotations from Ionide in Visual Studio Code, try changing your
  file extension from `.fs` to `.fsx` (or vice-versa) while developing

* If you get problems with `LexBuffer<byte>` expected
  `LexBuffer<char>`, make sure you have the unicode flag when running
  your lexer, i.e. `fslex --unicode <lexer>.fsl`

* If you have problems with "Lexing" namespace, try remove
  "Microsoft" from the namespace in your `.fsl` file, rerun `fslex
  --unicode <lexer>.fsl` and try again. If you are on Windows, you
  might NEED the "Microsoft." part in front of the Lexing namespace

